package ftn.sep.camunda.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.Editor;
import ftn.sep.model.PotentialReviewers;
import ftn.sep.model.Reviewer;
import ftn.sep.model.ScientificArea;
import ftn.sep.model.User;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.PotentialReviewersRepository;

@Service
public class FilterReviewersService implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private PotentialReviewersRepository potentialReviewersRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		
		PotentialReviewers reviewers = new PotentialReviewers();
		reviewers.setAllReviewers(this.findAllReviewers(article));
		Set<User> reviewersSet = this.findAreaReviewers(reviewers.getAllReviewers(), article.getArea());
		reviewers.setAreaReviewers(reviewersSet);
		if (reviewersSet.size() == 0) {
			execution.setVariable("editor", article.getJournal().getChiefEditor().getUsername());
		}
		reviewersSet = this.findSimilarArticleReviewrs(reviewers.getAllReviewers(), article);
		reviewers.setSimilarArticleReviewers(reviewersSet);
		reviewersSet = this.findDifferentRegionReviewers(reviewers.getAllReviewers(), article);
		reviewers.setDifferentRegionReviwers(reviewersSet);
		reviewers.setArticle(article);
		this.potentialReviewersRepository.save(reviewers);
		
		execution.setVariable("numberOfReviewers", 2);
		
		List<String> reviewersList = new ArrayList<>();
		execution.setVariable("reviewers", reviewersList);
		
		List<String> reviewersFinish = new ArrayList<>();
		execution.setVariable("reviewersFinish", reviewersFinish);
		
		execution.setVariable("firstCycle", true);
		
	}
	
	private Set<User> findAllReviewers(Article article) {
		Set<User> reviewers = new HashSet<>();
		for (String key : article.getJournal().getEditors().keySet()) {
			reviewers.add(article.getJournal().getEditors().get(key));
		}
		for (User user : article.getJournal().getReviewers()) {
			reviewers.add(user);
		}
		return reviewers;
	}

	private Set<User> findAreaReviewers(Set<User> allReviewers, ScientificArea area) {
		
		Set<User> reviewers = new HashSet<>();
		for (User user : allReviewers) {
			if (user instanceof Reviewer) {
				if (((Reviewer) user).getAreas().contains(area)) {
					reviewers.add(user);
				}
			}
			if (user instanceof Editor) {
				if (((Editor) user).getAreas().contains(area)) {
					reviewers.add(user);
				}
			}
		}
		
		return reviewers;
	}
	
	private Set<User> findSimilarArticleReviewrs(Set<User> allReviewers, Article a) {
		
		Set<User> reviewers = new HashSet<>();
		return reviewers;
		
	}
	
	private Set<User> findDifferentRegionReviewers(Set<User> allReviewers, Article a) {
		
		Set<User> reviewers = new HashSet<>();
		return reviewers;
		
	}

}
