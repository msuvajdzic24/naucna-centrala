package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.OrderEdition;

import java.util.Optional;

@Repository
public interface OrderEditionRepository extends CrudRepository<OrderEdition, Long> {

	OrderEdition findByPayerIdAndEditionIdAndPaid(Long id, Long id2, boolean b);

	Optional<OrderEdition> findByMerchantOrderId(String merchantOrderId);

}
