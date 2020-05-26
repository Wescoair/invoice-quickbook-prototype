package com.tcmis.invoice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcmis.invoice.entities.InvoicePrtCatalogLineView;
import com.tcmis.invoice.entities.id.InvoicePrtCatalogLineViewId;

public interface InvoicePrtCatalogLineViewDAO extends JpaRepository<InvoicePrtCatalogLineView, InvoicePrtCatalogLineViewId> {
	@Query("select new com.tcmis.invoice.entities.InvoicePrtCatalogLineView(v.itemId,"
																												+ "v.partDescription,"
																												+ "sum(v.unitOfSaleQuantity),"
																												+ "v.unitOfSalePrice)"
			   + " from #{#entityName} v"
			 + " where v.invoice = :invoice"
			 + " group by v.itemId, v.partDescription, v.unitOfSalePrice")
	List<InvoicePrtCatalogLineView> findByInvoice(@Param("invoice") String invoice);
}