package com.tecsup.petclinic.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.Column;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owners;
import com.tecsup.petclinic.domain.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
//Se realiza una prueba de la base de datos
@AutoConfigureTestDatabase(replace = Replace.NONE)
//El PetService se inyecta las nolese
public class OwnersServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OwnersServiceTest.class);

	@Autowired
	private OwnersService ownersService;
	
//	PRUEBA SI EL CODIGO 1 CORRESPONDE CON EL NOMBRE GEORGE EN LA BASE DE DATOS
	
	@Test
	public void testFindOwnersById() {

		long ID = 1;
		String NAME = "George";
		Owners owners = null;
		
		try {
			owners = ownersService.findById(ID);
		} catch (PetNotFoundException e) {
			fail(e.getMessage());
		}
		logger.info("" + owners);
//Valor esperado y lo que se obtiene
		
		assertEquals(NAME, owners.getFirstname());

	}
	
//	PRUEBA SI SOLO HAY UN PROPIETARIO CON EL NOMBRE DE GEORGE
	
	@Test
	public void testFindOwnersByFirstname() {

		String FIND_FIRSTNAME = "George";
		int SIZE_EXPECTED = 1;

		List<Owners> owners = ownersService.findByFirstname(FIND_FIRSTNAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
//	PRUEBA SI SOLO HAY UN PROPIETARIO CON EL APELLIDO FRANKLIN
	
	@Test
	public void testFindOwnersByLastname() {

		String FIND_LASTNAME = "Franklin";
		int SIZE_EXPECTED = 1;

		List<Owners> owners = ownersService.findByLastname(FIND_LASTNAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
//	PRUEBA SI SOLO HAY UN PROPIETARIO CON LA DIRECCION 110 W. Liberty St.
	
	@Test
	public void testFindOwnersByAddress() {

		String FIND_ADDRESS = "110 W. Liberty St.";
		int SIZE_EXPECTED = 1;

		List<Owners> owners = ownersService.findByAddress(FIND_ADDRESS);

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
//	PRUEBA SI SOLO HAY 4 OWNERS CON LA CITY MADISON
	
	@Test
	public void testFindOwnersByCity() {

		String FIND_CITY = "Madison";
		int SIZE_EXPECTED = 4;

		List<Owners> owners = ownersService.findByCity(FIND_CITY );

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
//	PRUEBA SI SOLO HAY SOLO UN PROPIETARIO CON EL NUMERO 6085551023
	
	@Test
	public void testFindOwnersByTelephone() {

		String FIND_TELEPHONE = "6085551023";
		int SIZE_EXPECTED = 1;

		List<Owners> owners = ownersService.findByTelephone(FIND_TELEPHONE);

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
//	CREACION DE UN OWNER EN LA BASE DE DATOS
	
	@Test
	public void testCreateOwners() {

		String OWNERS_FIRSTNAME = "Jonthan";
	    String  LAST_NAME = "Huarca";
		String  ADDRESS = "Villa El Salvador";
		String  CITY = "Lima";
		String  TELEPHONE = "986536574";
		
		Owners owner = new Owners(OWNERS_FIRSTNAME, LAST_NAME ,ADDRESS,CITY,TELEPHONE);
		owner = ownersService.create(owner);
		logger.info("" + owner);

		assertThat(owner.getId()).isNotNull();
		assertEquals(OWNERS_FIRSTNAME, owner.getFirstname());
		assertEquals(LAST_NAME, owner.getLastname());
		assertEquals(ADDRESS, owner.getAddress());
		assertEquals(CITY, owner.getCity());
		assertEquals(TELEPHONE, owner.getTelephone());
		
	}

//	ACTUALIZACION DE UN OWNERS EN LA BASE DE DATOS
	
	@Test
	public void testUpdateOwners() {

		String OWNERS_FIRSTNAME = "Steve";
	    String  LAST_NAME = "Torres";
		String  CITY = "Lima";
		String  ADDRESS = "San Juan";
		String  TELEPHONE = "9865365712";
		long create_id = -1;

		String  UP_OWNERS_FIRSTNAME = "Miguel";
	    String  UP_LAST_NAME = "Camacho";
		String  UP_ADDRESS = "Villa El Salvador";
		String  UP_CITY = "Lima";
		String  UP_TELEPHONE = "986531234";

		Owners owner = new Owners(OWNERS_FIRSTNAME,LAST_NAME,  ADDRESS ,CITY,TELEPHONE );

		// Create record
		logger.info(">" + owner);
		Owners readOwner = ownersService.create(owner);
		logger.info(">>" + readOwner);

		create_id = readOwner.getId();
		// Prepare data for update
		readOwner.setFirstname(UP_OWNERS_FIRSTNAME);
		readOwner.setLastname(UP_LAST_NAME);
		readOwner.setAddress(UP_ADDRESS);
		readOwner.setCity(UP_CITY);
		readOwner.setTelephone(UP_TELEPHONE);

		// Execute update
		Owners upgradeOwner = ownersService.update(readOwner);
		logger.info(">>>>" + upgradeOwner);

		assertThat(create_id).isNotNull();
		assertEquals(create_id, upgradeOwner.getId());
		
		assertEquals(UP_OWNERS_FIRSTNAME, upgradeOwner.getFirstname());
		assertEquals(UP_LAST_NAME, upgradeOwner.getLastname());
		assertEquals(UP_ADDRESS, upgradeOwner.getAddress());
		assertEquals(UP_CITY, upgradeOwner.getCity());
		assertEquals(UP_TELEPHONE, upgradeOwner.getTelephone());
		
	}

//	ELIMINACION DE UN OWNERS DE LA BASE DE DATOS
	
	@Test
	public void testDeleteOwners() {

		String OWNERS_FIRSTNAME = "Joel";
	    String  LAST_NAME = "Gonzales";
		String  ADDRESS = "Villa El Salvador";
		String  CITY = "Lima";
		String  TELEPHONE = "986534343";
		
		Owners owner = new Owners(OWNERS_FIRSTNAME, LAST_NAME ,ADDRESS,CITY,TELEPHONE);
		owner = ownersService.create(owner);
		logger.info("" + owner);

		try {
			ownersService.delete(owner.getId());
		} catch (PetNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownersService.findById(owner.getId());
			assertTrue(false);
		} catch (PetNotFoundException e) {
			assertTrue(true);
		} 
				
	}	
	
}
