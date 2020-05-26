package com.tcmis.invoice.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcmis.invoice.entities.InvoicePrtPtacLineView;
import com.tcmis.invoice.entities.id.InvoicePrtPtacLineViewId;

public interface InvoicePrtPtacLineViewDAO extends JpaRepository<InvoicePrtPtacLineView, InvoicePrtPtacLineViewId> {
	@Query("select new com.tcmis.invoice.entities.InvoicePrtPtacLineView(v.itemId,"
																												+ "v.partDescription,"
																												+ "sum(v.unitOfSaleQuantity),"
																												+ "v.unitOfSalePrice)"
			   + " from #{#entityName} v"
			 + " where v.invoice = :invoice"
			 + " group by v.itemId, v.partDescription, v.unitOfSalePrice")
	List<InvoicePrtPtacLineView> findByInvoice(@Param("invoice") BigDecimal invoice);
}