package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
