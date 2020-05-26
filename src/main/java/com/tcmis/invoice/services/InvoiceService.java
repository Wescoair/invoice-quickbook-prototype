package com.tcmis.invoice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcmis.invoice.dao.InvoicePrintCatalogViewDAO;
import com.tcmis.invoice.dao.InvoicePrintPtacViewDAO;
import com.tcmis.invoice.dao.InvoicePrtCatalogLineViewDAO;
import com.tcmis.invoice.dao.InvoicePrtPtacLineViewDAO;
import com.tcmis.invoice.entities.InvoiceLineView;
import com.tcmis.invoice.entities.InvoicePrintCatalogView;
import com.tcmis.invoice.entities.InvoicePrintPtacView;

@Service
public class InvoiceService {
	@Autowired
	private InvoicePrintCatalogViewDAO invoicePrintCatalogViewDAO;
	@Autowired
	private InvoicePrtCatalogLineViewDAO invoicePrtCatalogLineViewDAO;
	@Autowired
	private InvoicePrintPtacViewDAO invoicePrintPtacViewDAO;
	@Autowired
	private InvoicePrtPtacLineViewDAO invoicePrtPtacLineViewDAO;

	public InvoicePrintCatalogView getCatalogPriceInvoiceHeader(String invoiceNumber) {
		InvoicePrintCatalogView data = invoicePrintCatalogViewDAO.getOne(invoiceNumber);
		
		data.setInvoiceStr(data.getInvoice());
		data.setCompanyIdStr(data.getCompanyId());
		
		return data;
	}

	public List<InvoiceLineView> getCatalogPriceInvoiceLine(String invoiceNumber) {
		return new ArrayList<InvoiceLineView>(invoicePrtCatalogLineViewDAO.findByInvoice(invoiceNumber));
	}

	public InvoicePrintPtacView getPassThroughPriceInvoiceHeader(BigDecimal invoiceNumber) {
		InvoicePrintPtacView data = invoicePrintPtacViewDAO.getOne(invoiceNumber);
		
		data.setInvoiceStr(data.getInvoice().toPlainString());
		data.setCompanyIdStr(data.getCustomerId().toPlainString());
		
		return data;
	}

	public List<InvoiceLineView> getPassThroughPriceInvoiceLine(BigDecimal invoiceNumber) {
		return new ArrayList<InvoiceLineView>(invoicePrtPtacLineViewDAO.findByInvoice(invoiceNumber));
	}
}