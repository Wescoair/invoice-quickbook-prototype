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
public class InvoicePrintPtacView extends InvoiceView {
	private String billToAttention;
	private String billToName;
	private String calloutStatement;
	private String currencyId;
	private BigDecimal customerCurrencyExchRate;
	private String customerCurrencyId;
	private BigDecimal customerId;
	private String customerInvoice;
	private Date dateShipped;
	private String documentTitleText;
	private String homeCurrencyId;
	private BigDecimal homeExchangeRate;
	@Id
	private BigDecimal invoice;
	private String invoiceCalloutStatement;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private BigDecimal lastLineItem;
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
	private String opsEntityTaxRegistration;
	private String opsEntityTermsUrl;
	private String opsEntityWebsiteUrl;
	private String originalCustomerInvoice;
	private BigDecimal originalInvoice;
	private String periodEndDate;
	private String periodStartDate;
	private String poNumber;
	private String shipFromCountry;
	private String shipToCountry;
	private String shipToDescription;
	private String shipToName;
	private String taxCurrencyId;
	private BigDecimal taxExchangeRate;
	private String taxJurisdictionText;
	private String taxNotes;
	private String taxRegistrationNumber;
	private String taxRegistrationType;
	private String taxType;
	private BigDecimal totalGoods;
	private BigDecimal totalLineCharges;
	private BigDecimal totalServiceFee;
	private BigDecimal totalTaxAmount;
	private BigDecimal unitOfSaleQuantity;
}