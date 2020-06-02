package com.tecsup.petclinic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.domain.Owners;

@Repository
public interface OwnersRepository extends CrudRepository<Owners, Long>{

	
	List<Owners> findByFirstname(String firstname);

	// Fetch pets by typeId
	List<Owners> findByLastname(String lastname);
	// Fetch pets by ownerId
	List<Owners> findByAddress(String address);
	
	List<Owners> findByCity(String city);
	
	List<Owners> findByTelephone(String telephone);
	
}
