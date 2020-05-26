package com.tcmis.invoice.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.tcmis.invoice.entities.id.InvoicePrtPtacLineViewId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(InvoicePrtPtacLineViewId.class)
public class InvoicePrtPtacLineView extends InvoiceLineView {
	private String accountNumber;
	private String accountNumber2;
	private String accountNumber3;
	private String accountNumber4;
	private String application;
	private String applicationDesc;
	private String cabinetReplenishment;
	private String chargeType;
	private BigDecimal companyFacilityId;
	private String currencyId;
	private String customerInvoice;
	private String customerPartNo;
	private Date dateDelivered;
	private String departmentDesc;
	private String departmentId;
	private BigDecimal extendedAmount;
	private String facilityId;
	private String inventoryGroup;
	@Id
	private BigDecimal invoice;
	private BigDecimal invoiceAmount;
	private String invoiceGroup;
	@Id
	private BigDecimal invoiceLine;
	private BigDecimal invoicePeriod;
	private BigDecimal issueCostRevision;
	private String itemType;
	@Id
	private String lineItem;
	private String mfgLot;
	private String operatingEntityName;
	private String opsEntityLogoUrl;
	private String packaging;
	private BigDecimal percentSplitCharge;
	private String periodEndDate;
	private String periodStartDate;
	private String poNumber;
	@Id
	private BigDecimal prNumber;
	private BigDecimal radianPo;
	private String reportingEntityDesc;
	private BigDecimal requestor;
	private String rliNotes;
	private BigDecimal serviceFee;
	private String shipFromState;
	private String shipToState;
	private BigDecimal taxRate;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private String unitOfSale;
	
	public InvoicePrtPtacLineView(BigDecimal itemId,
													String partDescription,
													BigDecimal unitOfSalePrice,
													BigDecimal unitOfSaleQuantity) {
		setItemId(itemId);
		setPartDescription(partDescription);
		setUnitOfSalePrice(unitOfSalePrice);
		setUnitOfSaleQuantity(unitOfSaleQuantity);
	}
}