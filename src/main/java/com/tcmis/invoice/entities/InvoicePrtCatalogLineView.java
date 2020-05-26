package com.tcmis.invoice.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.tcmis.invoice.entities.id.InvoicePrtCatalogLineViewId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(InvoicePrtCatalogLineViewId.class)
public class InvoicePrtCatalogLineView extends InvoiceLineView {
	private String accountNumber;
	private String accountNumber2;
	private String application;
	private String applicationDesc;
	private String cabinetReplenishment;
	private String chargeType;
	private String currencyId;
	private String customerPartNo;
	private String customerPoLine;
	private Date dateDelivered;
	private String deliveryTicket;
	private String departmentDesc;
	private String examplePackaging;
	private BigDecimal exchangeRate;
	private BigDecimal extendedAmount;
	private String facilityId;
	private String inventoryGroup;
	@Id
	private String invoice;
	private Date invoiceDate;
	private String invoiceGroup;
	@Id
	private BigDecimal invoiceLine;
	private BigDecimal invoicePeriod;
	@Id
	private String lineItem;
	private String lotNumber;
	private BigDecimal materialAmount;
	private BigDecimal percentSplitCharge;
	private String periodEndDate;
	private String periodStartDate;
	private String poCurrencyId;
	private BigDecimal poLine;
	private String poNumber;
	@Id
	private BigDecimal prNumber;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
	private BigDecimal radianPo;
	private BigDecimal receiptId;
	private Date releaseDate;
	private String reportingEntityDesc;
	private BigDecimal serviceFee;
	private BigDecimal serviceFeeAmount;
	private BigDecimal shipmentId;
	private String shippingReference;
	private Date shipConfirmDate;
	private BigDecimal taxRate;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private String unitOfSale;
	
	public InvoicePrtCatalogLineView(BigDecimal itemId,
														String partDescription,
														BigDecimal unitOfSalePrice,
														BigDecimal unitOfSaleQuantity) {
		setItemId(itemId);
		setPartDescription(partDescription);
		setUnitOfSalePrice(unitOfSalePrice);
		setUnitOfSaleQuantity(unitOfSaleQuantity);
	}
}