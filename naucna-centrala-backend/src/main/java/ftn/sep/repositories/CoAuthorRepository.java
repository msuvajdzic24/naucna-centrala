package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.CoAuthor;

@Repository
public interface CoAuthorRepository extends CrudRepository<CoAuthor, Long> {

}
