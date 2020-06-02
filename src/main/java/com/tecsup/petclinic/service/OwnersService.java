package com.tecsup.petclinic.service;

import java.util.List;

import com.tecsup.petclinic.domain.Owners;
import com.tecsup.petclinic.exception.PetNotFoundException;

public interface OwnersService {

	
	Owners create(Owners owners);

	
	Owners update(Owners owners);

	
	void delete(Long id) throws PetNotFoundException;

	
	Owners findById(long id) throws PetNotFoundException;

	
	List<Owners> findByFirstname(String firstname);

	
	List<Owners> findByLastname(String lastname);
	
	
	List<Owners> findByAddress(String address);
	
	
	List<Owners> findByCity(String city);
	
	
	List<Owners> findByTelephone(String telephone);

	
	Iterable<Owners> findAll();
}
