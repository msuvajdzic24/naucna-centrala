package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

	Author findByUsername(String username);

}
