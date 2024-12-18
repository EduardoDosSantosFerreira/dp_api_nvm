package com.github.wesleyav.cloudparking;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.wesleyav.cloudparking.controller.dto.ParkingCreateDTO;

import io.restassured.RestAssured;

// Anotação para indicar que o teste deve ser executado em um ambiente de porta aleatória
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest extends AbstractContainerBase {

	// Anotação para injetar a porta aleatória do servidor
	@org.springframework.boot.test.web.server.LocalServerPort

	@LocalServerPort
	private int randomPort; // Porta aleatória para o servidor

	// Método executado antes de cada teste
	@BeforeEach
	public void SetUpTest() {
		// Configura a porta do RestAssured para a porta aleatória
		RestAssured.port = randomPort;
		System.out.println(randomPort); // Imprime a porta aleatória no console
	}

	// Teste para verificar se a busca por todos os registros retorna o status OK
	@Test
	void whenFindAllThenCheckResult() {
		RestAssured.given()
		.auth()
		.basic("user", "123") // Autenticação básica com usuário e senha
		.when()
		.get("/parking") // Faz uma requisição GET para o endpoint /parking
		.then()
		.statusCode(HttpStatus.OK.value()); // Verifica se o status da resposta é 200 OK
	}

	// Teste para verificar se a criação de um novo registro funciona corretamente
	@Test
	void whenCreateThenCheckIsCreated() {
		
		// Cria um objeto DTO para enviar na requisição
		ParkingCreateDTO createDTO = new ParkingCreateDTO();
		createDTO.setColor("VERMELHO");
		createDTO.setLicense("WRT-5555");
		createDTO.setModel("MACEIO");
		createDTO.setState("SP");
		
		RestAssured.given()
		.auth()
		.basic("user", "123") // Autenticação básica com usuário e senha
		.when()
		.contentType(MediaType.APPLICATION_JSON_VALUE) // Define o tipo de conteúdo como JSON
		.body(createDTO) // Define o corpo da requisição com o objeto DTO
		.post("/parking") // Faz uma requisição POST para o endpoint /parking
		.then()
		.statusCode(HttpStatus.CREATED.value()) // Verifica se o status da resposta é 201 CREATED
		.body("license", Matchers.equalTo("WRT-5555")) // Verifica se o campo license na resposta é igual a WRT-5555
		.body("color", Matchers.equalTo("VERMELHO")); // Verifica se o campo color na resposta é igual a VERMELHO
	}

}
