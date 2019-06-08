package ftn.sep.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class PotentialReviewers implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne
	Article article;
	
	@ManyToMany
	Set<User> allReviewers;
	
	@ManyToMany
	Set<User> areaReviewers;
	
	@ManyToMany
	Set<User> similarArticleReviewers;
	
	@ManyToMany
	Set<User> differentRegionReviwers;
	
	public PotentialReviewers() {
		this.allReviewers = new HashSet<>();
		this.areaReviewers = new HashSet<>();
		this.similarArticleReviewers = new HashSet<>();
		this.differentRegionReviwers = new HashSet<>();
	}

	public PotentialReviewers(Article article, Set<User> allReviewers, Set<User> areaReviewers,
			Set<User> similarArticleReviewers, Set<User> differentRegionReviwers) {
		super();
		this.article = article;
		this.allReviewers = allReviewers;
		this.areaReviewers = areaReviewers;
		this.similarArticleReviewers = similarArticleReviewers;
		this.differentRegionReviwers = differentRegionReviwers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Set<User> getAllReviewers() {
		return allReviewers;
	}

	public void setAllReviewers(Set<User> allReviewers) {
		this.allReviewers = allReviewers;
	}

	public Set<User> getAreaReviewers() {
		return areaReviewers;
	}

	public void setAreaReviewers(Set<User> areaReviewers) {
		this.areaReviewers = areaReviewers;
	}

	public Set<User> getSimilarArticleReviewers() {
		return similarArticleReviewers;
	}

	public void setSimilarArticleReviewers(Set<User> similarArticleReviewers) {
		this.similarArticleReviewers = similarArticleReviewers;
	}

	public Set<User> getDifferentRegionReviwers() {
		return differentRegionReviwers;
	}

	public void setDifferentRegionReviwers(Set<User> differentRegionReviwers) {
		this.differentRegionReviwers = differentRegionReviwers;
	}
	
}
