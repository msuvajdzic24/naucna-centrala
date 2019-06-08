package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Subscribe;

import java.util.Optional;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Long>  {

	Subscribe findBySubscriberIdAndJournalIdAndActive(Long id, Long journalId, boolean b);

	Optional<Subscribe> findByMerhcantOrderid(String merchantOrderId);

}
