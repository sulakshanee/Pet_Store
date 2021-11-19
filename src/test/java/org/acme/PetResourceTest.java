package org.acme;

import com.example.petstore.Pets;
import com.google.gson.Gson;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
@QuarkusTest
public class PetResourceTest {

	@Test
    public void testPetEndpoint() {
        given()
          .when().get("/v1/pets")
          .then()
             .statusCode(200);
//             .body(hasItem(
// 		            allOf(
//    		                hasEntry("pet_id", "1"),
//    		                hasEntry("pet_type", "Dog"),
//    		                hasEntry("pet_name", "Boola"),
//    		                hasEntry("pet_age", "3")
//    		            )
//    		      )
//    		 );
    }

    @Test
    public void testAddPet(){
        final Pets pets = new Pets();
        pets.setPetName("Charlie");
        pets.setPetAge(2);
        pets.setPetType("cat");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType()));
    }

    @Test
    public void testGetPets(){
        final Pets pets = new Pets();
        pets.setPetName("Charlie");
        pets.setPetAge(2);
        pets.setPetType("cat");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType()));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets")
                .then()
                .statusCode(200)
                .body("size", notNullValue());
    }

    @Test
    public void testUpdatePet(){
        final Pets pets = new Pets();
        pets.setPetName("Buddy");
        pets.setPetAge(2);
        pets.setPetType("cat");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);

        final Pets updatePets = new Pets( pets.getPetType(), "Buddy", pets.getPetAge());
        updatePets.setPetId(newPet.getPetId());

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatePets)
                .when().put("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(updatePets.getPetName()));
    }

    @Test
    public void testDeletePet(){
        final Pets pets = new Pets();
        pets.setPetName("Teddy");
        pets.setPetAge(2);
        pets.setPetType("cat");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().delete("/v1/pets/{id}", newPet.getPetId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindPetByName(){
        final Pets pets = new Pets();
        pets.setPetName("Leo");
        pets.setPetAge(2);
        pets.setPetType("cat");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/petName/{name}", newPet.getPetName())
                .then()
                .statusCode(200)
                .body("petName", is(newPet.getPetName()));
    }

    @Test
    public void testFindPetsByType(){
        final Pets pets = new Pets();
        pets.setPetName("Alfie");
        pets.setPetAge(2);
        pets.setPetType("cat");

        final String response = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pets)
                .when().post("/v1/pets")
                .then()
                .statusCode(200)
                .body("petName", is(pets.getPetName()))
                .body("petAge", is(pets.getPetAge()))
                .body("petType", is(pets.getPetType())).extract().asString();

        final Gson gson = new Gson();

        final Pets newPet = gson.fromJson(response,Pets.class);


        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/v1/pets/petType/{type}", newPet.getPetType())
                .then()
                .statusCode(200)
                .body("size", notNullValue());
    }


}