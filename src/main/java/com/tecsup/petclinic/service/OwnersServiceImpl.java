package com.tecsup.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.domain.Owners;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.domain.OwnersRepository;

@Service
public class OwnersServiceImpl  implements OwnersService {

	private static final Logger logger = LoggerFactory.getLogger(OwnersServiceImpl.class);

	@Autowired
	OwnersRepository ownersRepository;

	
	@Override
	public Owners create(Owners owners) {
		return ownersRepository.save(owners);
	}

	
	@Override
	public Owners update(Owners owners) {
		return ownersRepository.save(owners);
	}


	
	@Override
	public void delete(Long id) throws PetNotFoundException{

		Owners owners = findById(id);
		ownersRepository.delete(owners);

	}

	
	@Override
	public Owners findById(long id) throws PetNotFoundException {

		Optional<Owners> owners = ownersRepository.findById(id);

		if ( !owners.isPresent())
			throw new PetNotFoundException("Record not found...!");
			
		return owners.get();
	}

	
	@Override
	public List<Owners> findByFirstname(String firstname) {

		List<Owners> owners = ownersRepository.findByFirstname(firstname);

		owners.stream().forEach(owner -> logger.info("" + owner));

		return owners;
	}

	
	@Override
	public List<Owners> findByLastname(String lastname) {

		List<Owners> owners = ownersRepository.findByLastname(lastname);

		owners.stream().forEach(owner -> logger.info("" + owner));

		return owners;
	}

	
	@Override
	public List<Owners> findByAddress(String address) {

		List<Owners> owners = ownersRepository.findByAddress(address);

		owners.stream().forEach(owner -> logger.info("" + owner));

		return owners;
	}


	@Override
	public List<Owners> findByTelephone(String telephone) {
		// TODO Auto-generated method stub
		List<Owners> owners = ownersRepository.findByTelephone(telephone);

		owners.stream().forEach(owner -> logger.info("" + owner));

		return owners;
	}
	
	@Override
	public List<Owners> findByCity(String city) {
		// TODO Auto-generated method stub
		List<Owners> owners = ownersRepository.findByCity(city);

		owners.stream().forEach(owner -> logger.info("" + owner));

		return owners;
	}
	
	
	@Override
	public Iterable<Owners> findAll() {
		
		// TODO Auto-generated 
		return ownersRepository.findAll();
	
	}

	
}
