package com.tecsup.petclinic.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.Owners;
import com.tecsup.petclinic.domain.Pet;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class OwnersControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    
	@Autowired
	private MockMvc mockMvc;

	//PRUEBA SI EL PRIMER ID GRABASO EN LA BASE DE DATOS ES 1

	@Test
	public void testGetOwners() throws Exception {

		//int NRO_RECORD = 73;
		int ID_FIRST_RECORD = 1;
		
		this.mockMvc.perform(get("/owners"))
					.andExpect(status().isOk())
					.andExpect(content()
					.contentType(MediaType.APPLICATION_JSON_UTF8))
		//		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
					.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	
	//PRUEBA  SI EL OWNER DEL CODIGO 1 COINCIDE CON LOS PARAMETROS
	
	@Test
	public void testFindOnwersOK() throws Exception {


		String OWNERS_FIRSTNAME = "George";
	    String  LAST_NAME = "Franklin";
		String  ADDRESS = "110 W. Liberty St.";
		String  CITY = "Madison";
		String  TELEPHONE = "6085551023";
		
		mockMvc.perform(get("/owners/1"))  // Object must be BASIL 
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstname", is(OWNERS_FIRSTNAME)))
				.andExpect(jsonPath("$.lastname", is(LAST_NAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)))
				.andExpect(jsonPath("$.city", is(CITY)))
				.andExpect(jsonPath("$.telephone", is(TELEPHONE )));
			
	}


	// PRUEBA SI REALMENTE NO EXISTE EL OWNERS DE ID 666
	
	@Test
	public void testFindOwnersKO() throws Exception {

		mockMvc.perform(get("/owners/666"))
				.andExpect(status().isNotFound());

	}

	
	// CREA UN NUEVO OWNERS EN LA BASE DE DATOS
	
    @Test
    public void testCreateOwner() throws Exception {
		
    	String OWNERS_FIRSTNAME = "Jonthan";
	    String  LAST_NAME = "Huarca";
		String  ADDRESS = "Villa El Salvador";
		String  CITY = "Lima";
		String  TELEPHONE = "986536574";
		
		
		Owners newOwner = new Owners(OWNERS_FIRSTNAME,LAST_NAME, ADDRESS, CITY,TELEPHONE);
	
	    mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(newOwner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated())
	            //.andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.firstname", is(OWNERS_FIRSTNAME)))
	            .andExpect(jsonPath("$.lastname", is( LAST_NAME)))
	            .andExpect(jsonPath("$.address", is(ADDRESS)))
	            .andExpect(jsonPath("$.city", is(CITY)))
	            .andExpect(jsonPath("$.telephone", is(TELEPHONE)));		
    
	}
    
    //PRUEBA LA ACTUALIZACION DE UN OWNERS 
    @Test
    public void testUdateOwner() throws Exception {
    	
    //CREO UN NUEVO PET

    	String OWNERS_FIRSTNAME = "Karla";
	    String  LAST_NAME = "Torres";
		String  ADDRESS = "Sanjuan";
		String  CITY = "Lima";
		String  TELEPHONE = "986536574";
		
		
		Owners newOwner = new Owners(OWNERS_FIRSTNAME,LAST_NAME, ADDRESS, CITY,TELEPHONE);
	
		ResultActions mvcActions = mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(newOwner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated());
		
		//ACTUALIZO EL NUEVO PET
		

    	String  UP_OWNERS_FIRSTNAME = "Mario";
	    String  UP_LAST_NAME = "Villanueva";
		String  UP_ADDRESS = "Surco";
		String  UP_CITY = "Lima";
		String  UP_TELEPHONE = "986536574";
		
		
		Owners UP_Owner = new Owners(UP_OWNERS_FIRSTNAME ,UP_LAST_NAME, UP_ADDRESS, UP_CITY,UP_TELEPHONE);
		
		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.parse(response).read("$.id");
		
		ResultActions mvcActionsUP = mockMvc.perform(put("/owners/"+ id)
	            .content(om.writeValueAsString(UP_Owner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk());     
		
        
    }

    
    //   PRUEBA SI ELIMINA UN OWNER DESPUES DE HABERLO CREADO
    
    @Test
    public void testDeletePet() throws Exception {

    	String OWNERS_FIRSTNAME = "Khail";
	    String  LAST_NAME = "Mogollon";
		String  ADDRESS = "Santa Anita";
		String  CITY = "Lima";
		String  TELEPHONE = "986536574";
		
		
		Owners newOwner = new Owners(OWNERS_FIRSTNAME,LAST_NAME, ADDRESS, CITY,TELEPHONE);
		
	//PREGNTAR MAÑANA AL PROFESOR
		
		ResultActions mvcActions = mockMvc.perform(post("/owners")
	            .content(om.writeValueAsString(newOwner))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isCreated());
	            
		String response = mvcActions.andReturn().getResponse().getContentAsString();

		
		Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id ))
                /*.andDo(print())*/
                .andExpect(status().isOk());
    }
    
    
}