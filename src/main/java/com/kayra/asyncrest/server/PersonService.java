package com.kayra.asyncrest.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kayra.asyncrest.model.Person;

@Path("/person")
public class PersonService {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson() {
		Person person = new Person();
		person.setName("gurhan");
		person.setSurname("kucuk");
		return person;
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPerson(Person person){
		String result = "Person created:" + person;
		return Response.status(201).entity(result).build();
	}
}
