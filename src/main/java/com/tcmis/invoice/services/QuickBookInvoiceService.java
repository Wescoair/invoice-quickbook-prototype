package com.tcmis.invoice.services;

import org.springframework.stereotype.Service;

import com.intuit.ipp.data.Term;

@Service
public class QuickBookInvoiceService {
	public Term getPaymentTerm(String tcmISPaymentTerm) {
		Term term = new Term();
		term.setName(tcmISPaymentTerm);

		switch (tcmISPaymentTerm) {
			case "Net 30":
				term.setDueDays(30);
			case "Net 45":
				term.setDueDays(45);
			case "Net 60":
				term.setDueDays(60);
			case "Net 75":
				term.setDueDays(75);
			case "Net 120":
				term.setDueDays(120);
		}

		return term;
	}
}