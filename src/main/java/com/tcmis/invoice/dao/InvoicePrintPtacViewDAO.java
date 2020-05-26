package com.tcmis.invoice.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcmis.invoice.entities.InvoicePrintPtacView;

public interface InvoicePrintPtacViewDAO extends JpaRepository<InvoicePrintPtacView, BigDecimal> {}