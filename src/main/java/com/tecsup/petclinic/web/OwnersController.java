package com.tecsup.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.domain.Owners;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.service.OwnersService;


@RestController
public class OwnersController {

	@Autowired
	private OwnersService service;

	
	// @JsonIgnore
	@GetMapping("/owners")
	public Iterable<Owners> getOwners() {
		//
		return service.findAll();
	}

	

	@PostMapping("/owners")
	@ResponseStatus(HttpStatus.CREATED)
	Owners create(@RequestBody Owners newOwner) {
		return service.create(newOwner);
	}

	
	@GetMapping("/owners/{id}")
	ResponseEntity<Owners> findOne(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
		} catch (PetNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	
	@PutMapping("/owners/{id}")
	Owners saveOrUpdate(@RequestBody Owners newOwner, @PathVariable Long id) {
		Owners owner = null;
		try {
			owner = service.findById(id);
			owner.setFirstname(newOwner.getFirstname());
			owner.setLastname(newOwner.getLastname());
			owner.setAddress(newOwner.getAddress());
			owner.setCity(newOwner.getCity());
			owner.setTelephone(newOwner.getTelephone());
			service.update(owner);
		} catch (PetNotFoundException e) {
			owner = service.create(newOwner);
		}
		return owner;
	}

	
	@DeleteMapping("/owners/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			service.delete(id);
			return new ResponseEntity<>("" + id, HttpStatus.OK);
		} catch (PetNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}