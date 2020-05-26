package com.tcmis.invoice.entities;

import java.math.BigDecimal;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class InvoiceLineView {
	private BigDecimal itemId;
	private String partDescription;
	private BigDecimal unitOfSalePrice;
	private BigDecimal unitOfSaleQuantity;
}