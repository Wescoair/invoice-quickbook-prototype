package com.tcmis.invoice.entities.id;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePrtPtacLineViewId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String invoice;
	private BigDecimal invoiceLine;
	private String lineItem;
	private BigDecimal prNumber;
}
