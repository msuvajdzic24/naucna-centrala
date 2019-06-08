package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String string);

}
