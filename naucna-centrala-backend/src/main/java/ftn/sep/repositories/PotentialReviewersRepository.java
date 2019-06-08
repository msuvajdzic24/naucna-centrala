package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.PotentialReviewers;

@Repository
public interface PotentialReviewersRepository extends CrudRepository<PotentialReviewers, Long> {

	PotentialReviewers findByArticleId(Long articleId);

}
