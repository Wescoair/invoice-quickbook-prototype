package com.tcmis.invoice.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountTypeEnum;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.MemoRef;
import com.intuit.ipp.data.PhysicalAddress;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.InvalidTokenException;
import com.intuit.ipp.security.OAuth2Authorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;
import com.tcmis.invoice.configuration.properties.ApplicationProperties;
import com.tcmis.invoice.entities.InvoiceLineView;
import com.tcmis.invoice.entities.InvoiceView;
import com.tcmis.invoice.services.InvoiceService;
import com.tcmis.invoice.services.QuickBookInvoiceService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dderose
 *
 */
@Slf4j
@RestController
public class InvoiceController {
	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	QuickBookInvoiceService qbService;

	/**
	 * Sample QBO API call using OAuth2 tokens
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/invoice")
	public String callInvoicingConcept(@RequestParam String invoiceNumber, 
														HttpSession session) {

		String realmId = (String) session.getAttribute("realmId");
		if (StringUtils.isEmpty(realmId)) {
			return new JSONObject().put("response", "No realm ID.  QBO calls only work if the accounting scope was passed!")
													.toString();
		}
		String accessToken = (String) session.getAttribute("access_token");

		try {
			// get DataService
			DataService service = getDataService(realmId, accessToken);
			
			log.info("Get data from database");
			InvoiceView data;
			List<InvoiceLineView> dataLine;
			
			data = invoiceService.getCatalogPriceInvoiceHeader(invoiceNumber);
			dataLine = invoiceService.getCatalogPriceInvoiceLine(invoiceNumber);

//			BigDecimal invoiceNum = new BigDecimal(invoiceNumber);
//			data = invoiceService.getPassThroughPriceInvoiceHeader(invoiceNum);
//			dataLine = invoiceService.getPassThroughPriceInvoiceLine(invoiceNum);
			
			log.info("Get customer data");
			Customer customer = getCustomerFields(service, data);
			
			log.info("Get items data");
			Map<BigDecimal, Item> itemMap = new HashMap<>();
			for (InvoiceLineView line : dataLine) {
				if (!itemMap.containsKey(line.getItemId()) ) {
					Item item = getItemFields(service, line.getItemId().toPlainString());
					itemMap.put(line.getItemId(), item);
				}
			}

			log.info("Get payment term data");
			Term term = getPaymentTerms(service, data.getPaymentTerms());

			log.info("Prepare invoice");
			Invoice savedInvoice = getInvoice(service, customer, term, data, dataLine, itemMap);
			 
		    try {
				log.info("Print invoice");
				Files.copy(service.downloadPDF(savedInvoice),
								new File(applicationProperties.getInvoiceSaveDirectory() + "targetFile.pdf").toPath(),
					    		StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				log.error("Fail to save PDF", e);
			}
		    
			return createResponse(savedInvoice);
		} catch (InvalidTokenException e) {
			return new JSONObject().put("response", "InvalidToken - Refreshtoken and try again").toString();
		} catch (FMSException e) {
			String errorMsg = "";
			for (Error error : e.getErrorList()) {
				log.error("Error while calling the API :: " + error.getMessage(), error);
				errorMsg += error.getMessage() + "\n";
			}
			
			errorMsg = StringUtils.chomp(errorMsg);
			
			return new JSONObject().put("response", errorMsg).toString();
		}
	}

	/**
	 * Create Customer request
	 * 
	 * @return
	 * @throws FMSException 
	 */
	private Customer getCustomerFields(DataService service, InvoiceView data) throws FMSException {
		String sql = "select * from customer where displayname = '" + data.getCompanyIdStr() + "'";
		QueryResult queryResult = service.executeQuery(sql);
		if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
			return (Customer) queryResult.getEntities().get(0);
		} else {
			Customer customer = new Customer();
			customer.setDisplayName(data.getCompanyIdStr());
			customer.setCompanyName(data.getCompanyIdStr());
			
			PhysicalAddress billToAddress = new PhysicalAddress();
			billToAddress.setLine1(data.getBillToAddressLine1());
			billToAddress.setLine2(data.getBillToAddressLine2());
			billToAddress.setLine3(data.getBillToAddressLine3());
			billToAddress.setLine4(data.getBillToAddressLine4());
			billToAddress.setLine5(data.getBillToAddressLine5());
			customer.setBillAddr(billToAddress);
			
			PhysicalAddress shipToAddress = new PhysicalAddress();
			shipToAddress.setLine1(data.getShipToAddressLine1());
			shipToAddress.setLine2(data.getShipToAddressLine2());
			shipToAddress.setLine3(data.getShipToAddressLine3());
			shipToAddress.setLine4(data.getShipToAddressLine4());
			shipToAddress.setLine5(data.getShipToAddressLine5());
			customer.setShipAddr(shipToAddress);
			
			return service.add(customer);
		}
	}

	/**
	 * Create Item request
	 * 
	 * @param service
	 * @return
	 * @throws FMSException
	 */
	private Item getItemFields(DataService service, String itemId) throws FMSException {
		String sql = "select * from item where name = '" + itemId + "'";
		QueryResult queryResult = service.executeQuery(sql);
		if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
			return (Item) queryResult.getEntities().get(0);
		} else {
			Item item = new Item();
			item.setName(itemId);
			
			item.setType(ItemTypeEnum.NON_INVENTORY);
			
			Account incomeAccount = getIncomeBankAccount(service);
			item.setIncomeAccountRef(createRef(incomeAccount));
			
			return service.add(item);
		}
	}
	
	/**
	 * Get Income account
	 * 
	 * @param service
	 * @return
	 * @throws FMSException
	 */
	private Account getIncomeBankAccount(DataService service) throws FMSException {
		QueryResult queryResult = service.executeQuery(String.format("select * from Account where AccountType='%s' maxresults 1", AccountTypeEnum.INCOME.value()));
		List<? extends IEntity> entities = queryResult.getEntities();
		if (!entities.isEmpty()) {
			return (Account) entities.get(0);
		}
		return createIncomeBankAccount(service);
	}

	/**
	 * Create Income account
	 * 
	 * @param service
	 * @return
	 * @throws FMSException
	 */
	private Account createIncomeBankAccount(DataService service) throws FMSException {
		Account account = new Account();
		account.setName("Incom" + RandomStringUtils.randomAlphabetic(5));
		account.setAccountType(AccountTypeEnum.INCOME);

		return service.add(account);
	}

	/**
	 * Get Income account
	 * 
	 * @param service
	 * @return
	 * @throws FMSException
	 */
	private Term getPaymentTerms(DataService service, String paymentTerms) throws FMSException {
		String sql = "select * from term where name = '" + paymentTerms + "'";
		QueryResult queryResult = service.executeQuery(sql);
		if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
			return (Term) queryResult.getEntities().get(0);
		} else {
			Term term = qbService.getPaymentTerm(paymentTerms);
			
			return service.add(term);
		}
	}

	/**
	 * Prepare Invoice request
	 * 
	 * @param customer
	 * @param item
	 * @return
	 */
	private Invoice getInvoiceFields(Customer customer, Term term, InvoiceView data) {
		Invoice invoice = new Invoice();
		invoice.setCustomerRef(createRef(customer));
		invoice.setTxnDate(data.getInvoiceDate());
		invoice.setDueDate(data.getPaymentDueDate());
		invoice.setDocNumber(data.getInvoiceStr());
		invoice.setSalesTermRef(createRef(term));
		
		MemoRef memo = new MemoRef();
		memo.setValue(StringUtils.join(Arrays.asList("Remittance Instructions:",
																			data.getRemittanceInstructionLine1(),
																			data.getRemittanceInstructionLine2(),
																			data.getRemittanceInstructionLine3(),
																			data.getRemittanceInstructionLine4(),
																			data.getRemittanceInstructionLine5(),
																			data.getRemittanceInstructionLine6(),
																			data.getRemittanceInstructionLine7(),
																			data.getRemittanceInstructionLine8(),
																			data.getRemittanceInstructionLine9(),
																			data.getRemittanceInstructionLine10()),
														"\n"));
		invoice.setCustomerMemo(memo);

		return invoice;
	}
	
	private Line addItemToInvoice(Invoice invoice, Item item, InvoiceLineView dataLine) {
		Line line = new Line();
		if (dataLine.getUnitOfSaleQuantity() != null && dataLine.getUnitOfSalePrice() != null) {
			line.setAmount(dataLine.getUnitOfSaleQuantity().multiply(dataLine.getUnitOfSalePrice()));
		} else {
			line.setAmount(BigDecimal.ZERO);
		}
		line.setDescription(dataLine.getPartDescription());
		line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);

		SalesItemLineDetail silDetails = new SalesItemLineDetail();
		silDetails.setItemRef(createRef(item));
		silDetails.setQty(dataLine.getUnitOfSaleQuantity());
		silDetails.setUnitPrice(dataLine.getUnitOfSalePrice());

		line.setSalesItemLineDetail(silDetails);
		
		return line;
	}
	
	private Invoice getInvoice(DataService service,
											Customer customer,
											Term term,
											InvoiceView data,
											List<InvoiceLineView> dataLine,
											Map<BigDecimal, Item> itemMap) throws FMSException {
		log.info("Get invoice data");
		String sql = "select * from invoice where docnumber = '" + data.getInvoiceStr() + "'";
		QueryResult queryResult = service.executeQuery(sql);
		if (!queryResult.getEntities().isEmpty() && queryResult.getEntities().size() > 0) {
			return (Invoice) queryResult.getEntities().get(0);
		} else {
			log.info("Create invoice");
			Invoice invoice = getInvoiceFields(customer, term, data);
			List<Line> invoiceLines = new ArrayList<Line>();
			log.info("Add invoice details");
			for (InvoiceLineView line : dataLine) {
				invoiceLines.add(addItemToInvoice(invoice, itemMap.get(line.getItemId()), line));
			}
			invoice.setLine(invoiceLines);
			
			return service.add(invoice);
		}
	}

	/**
	 * Creates reference type for an entity
	 * 
	 * @param entity - IntuitEntity object inherited by each entity
	 * @return
	 */
	private ReferenceType createRef(IntuitEntity entity) {
		ReferenceType referenceType = new ReferenceType();
		referenceType.setValue(entity.getId());
		return referenceType;
	}

	/**
	 * Map object to json string
	 * 
	 * @param entity
	 * @return
	 */
	private String createResponse(Object entity) {
		try {
			return new ObjectMapper().writeValueAsString(entity);
		}  catch (Exception e) {
			log.error("Exception while calling QBO ", e);
			
			return new JSONObject().put("response", "Failed").toString();
		}
	}

	public DataService getDataService(String realmId, String accessToken) throws FMSException {
		Config.setProperty(Config.BASE_URL_QBO, applicationProperties.getQuickBooksCompanyUrl());
		// create context
		Context context = new Context(new OAuth2Authorizer(accessToken), ServiceType.QBO, realmId);

		// create dataservice
		return new DataService(context);
	}
}