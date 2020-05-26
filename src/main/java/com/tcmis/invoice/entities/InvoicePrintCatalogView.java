package com.tcmis.invoice.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvoicePrintCatalogView extends InvoiceView {
	private String billingEntityAddressLine1;
	private String billingEntityAddressLine2;
	private String billingEntityAddressLine3;
	private String billingEntityAddressLine4;
	private String billingEntityAddressLine5;
	private String billingEntityAttention;
	private String billingEntityEmail;
	private String billingEntityFax;
	private String billingEntityPhone;
	private String billToAttention;
	private String billToCompanyId;
	private String billToCustomerName;
	private String billToLocationId;
	private String billToTaxId;
	private String billToTaxId2;
	private String billToTaxId3;
	private String billToTaxId4;
	private String billToTaxType;
	private String billToTaxType2;
	private String billToTaxType3;
	private String billToTaxType4;
	private String calloutStatement;
	private String companyId;
	private String countryOfOrigin;
	private BigDecimal customerCurrencyExchRate;
	private String customerCurrencyId;
	private BigDecimal customerId;
	private String customerInvoice;
	private String customerPo;
	private String dateShipped;
	private String documentTitleText;
	private BigDecimal exchangeRate;
	private String facilityId;
	private String incoterms;
	@Id
	private String invoice;
	private BigDecimal invoiceAmount;
	private String invoiceCalloutStatement;
	private String invoiceCurrencyId;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private String localeCode;
	private String localCurrencyId;
	private String operatingEntityName;
	private String opsEntityAddressLine1;
	private String opsEntityAddressLine2;
	private String opsEntityAddressLine3;
	private String opsEntityAddressLine4;
	private String opsEntityAddressLine5;
	private String opsEntityAttention;
	private String opsEntityEmail;
	private String opsEntityFax;
	private String opsEntityId;
	private String opsEntityLogoUrl;
	private String opsEntityPhone;
	private String opsEntityTaxAuthCurrency;
	private BigDecimal opsEntityTaxExchRate;
	private String opsEntityTaxId;
	private String opsEntityTaxId2;
	private String opsEntityTaxId3;
	private String opsEntityTaxId4;
	private String opsEntityTaxRegistration;
	private String opsEntityTaxType;
	private String opsEntityTaxType2;
	private String opsEntityTaxType3;
	private String opsEntityTaxType4;
	private String opsEntityTermsUrl;
	private String opsEntityWebsiteUrl;
	private String originalCustomerInvoice;
	private BigDecimal originalInvoice;
	private Date periodEndDate;
	private Date periodStartDate;
	private String shippingMethod;
	private String shipFromCountry;
	private String shipToAttention;
	private String shipToCountry;
	private String shipToCustomerName;
	private String shipToLocationId;
	private BigDecimal subtotal;
	private String supplierCode;
	private BigDecimal taxAmount;
	private BigDecimal taxAmount2;
	private String taxCurrencyId;
	private BigDecimal taxExchangeRate;
	private String taxJurisdictionText;
	private BigDecimal taxRate;
	private BigDecimal taxRate2;
	private String taxType;
	private String taxType2;
	private BigDecimal totalGoods;
	private BigDecimal totalLineCharges;
	private BigDecimal totalServiceFee;
	private BigDecimal totalTax;
	private BigDecimal unitOfSaleQuantity;
}