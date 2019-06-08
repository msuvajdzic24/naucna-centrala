package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Journal;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Long> {

	Journal findByName(String journal);

}
