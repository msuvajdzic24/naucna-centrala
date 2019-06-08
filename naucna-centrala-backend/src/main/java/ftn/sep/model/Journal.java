package ftn.sep.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Journal implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String isbn;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ScientificArea> areas;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private JournalType type;
	
	@OneToOne
	private Editor chiefEditor;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Map<String, Editor> editors;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Reviewer> reviewers;
	
	@Column(nullable = false)
	private double editionPrice;
	
	@Column
	private double articlePrice;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column
	private String merchantId;
	
	public Journal() {
		this.areas = new HashSet<>();
		this.editors = new HashMap<>();
		this.reviewers = new HashSet<>();
	}

	public Journal(String name, String isbn, Set<ScientificArea> areas, JournalType type, Editor chiefEditor,
			Map<String, Editor> editors, Set<Reviewer> reviewers, double editionPrice, double articlePrice,
			Currency currency) {
		super();
		this.name = name;
		this.isbn = isbn;
		this.areas = areas;
		this.type = type;
		this.chiefEditor = chiefEditor;
		this.editors = editors;
		this.reviewers = reviewers;
		this.editionPrice = editionPrice;
		this.articlePrice = articlePrice;
		this.currency = currency;
	}
	
	public Journal(String name, String isbn, Set<ScientificArea> areas, JournalType type, Editor chiefEditor,
			Map<String, Editor> editors, Set<Reviewer> reviewers, double editionPrice, double articlePrice,
			Currency currency, String merchantId) {
		super();
		this.name = name;
		this.isbn = isbn;
		this.areas = areas;
		this.type = type;
		this.chiefEditor = chiefEditor;
		this.editors = editors;
		this.reviewers = reviewers;
		this.editionPrice = editionPrice;
		this.articlePrice = articlePrice;
		this.currency = currency;
		this.merchantId = merchantId;
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

	public Set<ScientificArea> getAreas() {
		return areas;
	}

	public void setAreas(Set<ScientificArea> areas) {
		this.areas = areas;
	}

	public JournalType getType() {
		return type;
	}

	public void setType(JournalType type) {
		this.type = type;
	}

	public Editor getChiefEditor() {
		return chiefEditor;
	}

	public void setChiefEditor(Editor chiefEditor) {
		this.chiefEditor = chiefEditor;
	}

	public Map<String, Editor> getEditors() {
		return editors;
	}

	public void setEditors(Map<String, Editor> editors) {
		this.editors = editors;
	}

	public Set<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<Reviewer> reviewers) {
		this.reviewers = reviewers;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
