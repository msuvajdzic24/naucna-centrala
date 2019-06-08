package ftn.sep.services;

import java.util.List;

import org.springframework.core.io.Resource;

import ftn.sep.dto.DTOResubmitArticle;
import ftn.sep.dto.DTOSubmitArticle;
import ftn.sep.model.Article;
import ftn.sep.model.OrderArticle;

public interface ArticleService {
	
	public Article getArticle(Long id);
	public List<Article> getArticles();
	public boolean canBuyArticle(Long articleId, String username);
	public OrderArticle createOrderArticle(Long articleId, String username);
	public void confirmOrderArticle(String orderId);
	public Article submit(DTOSubmitArticle dtoSA, String name);
	public Resource loadFileAsResource(Long articleId);
	public Article resubmit(Long articleId, DTOResubmitArticle dtoRA, String name);

}
