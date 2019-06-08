package ftn.sep.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@ManyToOne
	private Author submitter;
	
	@ManyToMany
	private Set<CoAuthor> authors;
	
	@Column(nullable = false)
	private String articleAbstract;
	
	@ManyToMany
	private Set<Keyword> keywords;
	
	@ManyToOne
	private ScientificArea area;
	
	@ManyToOne
	private Journal journal;
	
	@ManyToOne
	private Edition edition;
	
	@Column(nullable = false)
	private String filename;
	
	@Column
	private String doi;
	
	public Article() {
		this.authors = new HashSet<>();
		this.keywords = new HashSet<>();
	}
	
	public Article(String title, Author submitter, Set<CoAuthor> authors, String articleAbstract, Set<Keyword> keywords,
			ScientificArea area) {
		super();
		this.title = title;
		this.submitter = submitter;
		this.authors = authors;
		this.articleAbstract = articleAbstract;
		this.keywords = keywords;
		this.area = area;
	}

	public Article(String title, Author submitter, Set<CoAuthor> authors, String articleAbstract, Set<Keyword> keywords,
			ScientificArea area, Journal journal, Edition edition, String filename, String doi) {
		super();
		this.title = title;
		this.submitter = submitter;
		this.authors = authors;
		this.articleAbstract = articleAbstract;
		this.keywords = keywords;
		this.area = area;
		this.journal = journal;
		this.edition = edition;
		this.filename = filename;
		this.doi = doi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getSubmitter() {
		return submitter;
	}

	public void setSubmitter(Author submitter) {
		this.submitter = submitter;
	}

	public Set<CoAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<CoAuthor> authors) {
		this.authors = authors;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<Keyword> keywords) {
		this.keywords = keywords;
	}

	public ScientificArea getArea() {
		return area;
	}

	public void setArea(ScientificArea area) {
		this.area = area;
	}
	
	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}
	
}
