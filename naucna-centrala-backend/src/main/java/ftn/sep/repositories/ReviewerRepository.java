package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Reviewer;

@Repository
public interface ReviewerRepository extends CrudRepository<Reviewer, Long> {

}
