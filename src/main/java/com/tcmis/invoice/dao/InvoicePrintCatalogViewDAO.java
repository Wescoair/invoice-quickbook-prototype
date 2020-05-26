package com.tcmis.invoice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcmis.invoice.entities.InvoicePrintCatalogView;

public interface InvoicePrintCatalogViewDAO extends JpaRepository<InvoicePrintCatalogView, String> {}