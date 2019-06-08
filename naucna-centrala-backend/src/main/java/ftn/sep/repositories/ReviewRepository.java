package ftn.sep.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

	List<Review> findByArticleId(Long articleId);
	List<Review> findByArticleIdAndOld(Long articleId, boolean b);

}
