package ftn.sep.dtoreturn;

import java.util.HashSet;
import java.util.Set;

import ftn.sep.model.Currency;
import ftn.sep.model.Journal;
import ftn.sep.model.JournalType;
import ftn.sep.model.ScientificArea;

public class DTOJournalCamReturn {
	
	private Long id;
	private String name;
	private String isbn;
	private Set<String> areas;
	private JournalType type;
	private double editionPrice;
	private double articlePrice;
	private Currency currency;
	private String chiefEditor;
	
	public DTOJournalCamReturn() {
		this.areas = new HashSet<>();
	}

	public DTOJournalCamReturn(Long id, String name, String isbn, Set<String> areas, JournalType type,
			double editionPrice, double articlePrice, Currency currency, String chiefEditor) {
		super();
		this.id = id;
		this.name = name;
		this.isbn = isbn;
		this.areas = areas;
		this.type = type;
		this.editionPrice = editionPrice;
		this.articlePrice = articlePrice;
		this.currency = currency;
		this.chiefEditor = chiefEditor;
	}

	public static DTOJournalCamReturn convert(Journal journal) {
		Set<String> areas = new HashSet<>();
		for (ScientificArea area : journal.getAreas()) {
			areas.add(area.getName());
		}
		DTOJournalCamReturn dtoJR = new DTOJournalCamReturn(journal.getId(), journal.getName(), journal.getIsbn(), 
				areas, journal.getType(), journal.getEditionPrice(), journal.getArticlePrice(), journal.getCurrency(), 
				journal.getChiefEditor().getUsername());
		return dtoJR;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<String> getAreas() {
		return areas;
	}

	public void setAreas(Set<String> areas) {
		this.areas = areas;
	}

	public JournalType getType() {
		return type;
	}

	public void setType(JournalType type) {
		this.type = type;
	}

	public double getEditionPrice() {
		return editionPrice;
	}

	public void setEditionPrice(double editionPrice) {
		this.editionPrice = editionPrice;
	}

	public double getArticlePrice() {
		return articlePrice;
	}

	public void setArticlePrice(double articlePrice) {
		this.articlePrice = articlePrice;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getChiefEditor() {
		return chiefEditor;
	}

	public void setChiefEditor(String chiefEditor) {
		this.chiefEditor = chiefEditor;
	}

}
