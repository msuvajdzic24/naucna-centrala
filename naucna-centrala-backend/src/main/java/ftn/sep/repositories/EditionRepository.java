package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Edition;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Long> {

}
