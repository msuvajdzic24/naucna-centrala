package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.OrderArticle;

import java.util.Optional;

@Repository
public interface OrderArticleRepository extends CrudRepository<OrderArticle, Long> {

	OrderArticle findByPayerIdAndArticleIdAndPaid(Long id, Long articleId, boolean paid);

	Optional<OrderArticle> findByMerchantOrderId(String merchantOrderId);

}
