package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.ScientificArea;

@Repository
public interface ScientificAreaRepository extends CrudRepository<ScientificArea, Long> {

	ScientificArea findByName(String area);

}
