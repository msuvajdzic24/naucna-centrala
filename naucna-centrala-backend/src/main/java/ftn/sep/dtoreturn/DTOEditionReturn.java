package ftn.sep.dtoreturn;

import ftn.sep.model.Currency;
import ftn.sep.model.Edition;
import ftn.sep.model.JournalType;

public class DTOEditionReturn {

	private Long id;
	private int number;
	private String journal;
	private JournalType journalType;
	private double price;
	private Currency currency;
	private boolean buy;
	
	public DTOEditionReturn() {
	}

	public DTOEditionReturn(Long id, int number, String journal, JournalType journalType, double price,
			Currency currency) {
		super();
		this.id = id;
		this.number = number;
		this.journal = journal;
		this.journalType = journalType;
		this.price = price;
		this.currency = currency;
	}

	public DTOEditionReturn(Long id, int number, String journal, JournalType journalType, double price,
			Currency currency, boolean buy) {
		super();
		this.id = id;
		this.number = number;
		this.journal = journal;
		this.journalType = journalType;
		this.price = price;
		this.currency = currency;
		this.buy = buy;
	}

	public static DTOEditionReturn convert(Edition e) {
		DTOEditionReturn dtoER = new DTOEditionReturn(e.getId(), e.getNumber(), e.getJournal().getName(), 
				e.getJournal().getType(), e.getJournal().getEditionPrice(), e.getJournal().getCurrency(), true);
		return dtoER;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	public JournalType getJournalType() {
		return journalType;
	}

	public void setJournalType(JournalType journalType) {
		this.journalType = journalType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public boolean isBuy() {
		return buy;
	}

	public void setBuy(boolean buy) {
		this.buy = buy;
	}
	
}
