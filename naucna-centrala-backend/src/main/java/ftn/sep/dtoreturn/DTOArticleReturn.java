package ftn.sep.dtoreturn;

import ftn.sep.model.Article;
import ftn.sep.model.Currency;

public class DTOArticleReturn {

	private Long id;
	private String name;
	private String author;
	private String area;
	private String journal;
	private int edition;
	private double price;
	private Currency currency;
	private boolean buy;
	
	public DTOArticleReturn() {
	}
	
	public DTOArticleReturn(Long id, String name, String author, String area, String journal, int edition, double price,
			Currency currency) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.area = area;
		this.journal = journal;
		this.edition = edition;
		this.price = price;
		this.currency = currency;
	}

	public DTOArticleReturn(Long id, String name, String author, String area, String journal, int edition, double price,
			Currency currency, boolean buy) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.area = area;
		this.journal = journal;
		this.edition = edition;
		this.price = price;
		this.currency = currency;
		this.buy = buy;
	}

	public static DTOArticleReturn convert(Article a) {
		String author = a.getSubmitter().getName() + " " + a.getSubmitter().getSurname();
		DTOArticleReturn dtoAR = new DTOArticleReturn(a.getId(), a.getTitle(), author, a.getArea().getName(),
				a.getEdition().getJournal().getName(), a.getEdition().getNumber(),
				a.getEdition().getJournal().getArticlePrice(), a.getEdition().getJournal().getCurrency(), true);
		return dtoAR;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
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
