package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Keyword;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Long> {

}
