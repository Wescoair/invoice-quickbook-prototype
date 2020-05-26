package com.tcmis.invoice.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class InvoiceView {
	private String billToAddressLine1;
	private String billToAddressLine2;
	private String billToAddressLine3;
	private String billToAddressLine4;
	private String billToAddressLine5;
	@Transient
	private String companyIdStr;
	@Transient
	private String invoiceStr;
	private Date invoiceDate;
	private Date paymentDueDate;
	private String paymentTerms;
	private String remittanceInstructionLine1;
	private String remittanceInstructionLine10;
	private String remittanceInstructionLine2;
	private String remittanceInstructionLine3;
	private String remittanceInstructionLine4;
	private String remittanceInstructionLine5;
	private String remittanceInstructionLine6;
	private String remittanceInstructionLine7;
	private String remittanceInstructionLine8;
	private String remittanceInstructionLine9;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
}
