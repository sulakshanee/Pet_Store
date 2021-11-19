package com.example.petstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	private final PetRepository petRepository;

	public PetResource(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Adding Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@POST
	@Path("")
	public Response createPet(@RequestBody Pets pets){
		return Response.ok(petRepository.save(pets)).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Get All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@GET
	@Path("")
	public Response getAllPets(){
		ArrayList<Pets> petsArrayList = new ArrayList<>();
		petsArrayList = (ArrayList<Pets>) petRepository.findAll();
		return Response.ok(petsArrayList).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Update Pets Details", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pets"))) })
	@PUT
	@Path("")
	public Response updatePetsDetails(@RequestBody Pets pets){
		Optional<Pets> pets1 = Optional.ofNullable(petRepository.findById(pets.getPetId())
				.orElseThrow(() -> new NotFoundException("Could not pet using given id")));
		pets1.get().setPetName(pets.getPetName());
		pets1.get().setPetAge(pets.getPetAge());
		pets1.get().setPetType(pets.getPetType());
		return Response.ok(petRepository.save(pets1.get())).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "Delete Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@DELETE
	@Path("/{id}")
	public Response findPetsByPetType(@PathParam("id") Long id){
		petRepository.deleteById(id);
		return Response.ok().build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search Pet by name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("/petName/{name}")
	public Response findPetsByName(@PathParam("name") String name){
		return Response.ok(petRepository.findByPetName(name).get()).build();

	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Search Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	@Path("petType/{type}")
	public Response findPetsByPetType(@PathParam("type") String type){
		List<Pets> petList = petRepository.findByPetType(type);
		return Response.ok(petList).build();

	}

}
