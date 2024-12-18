package com.github.wesleyav.cloudparking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyav.cloudparking.controller.dto.ParkingCreateDTO;
import com.github.wesleyav.cloudparking.controller.dto.ParkingDTO;
import com.github.wesleyav.cloudparking.controller.mapper.ParkingMapper;
import com.github.wesleyav.cloudparking.model.Parking;
import com.github.wesleyav.cloudparking.service.ParkingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/parking")
@Tag(name = "ParkingController")
public class ParkingController {

	private final ParkingService parkingService;
	private final ParkingMapper parkingMapper;

	// Construtor do controlador, inicializa o serviço de estacionamento e o mapeador
	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
		this.parkingMapper = new ParkingMapper();
	}

	// Endpoint para listar todos os estacionamentos
	@GetMapping
	@Operation(summary = "Endpoint to list parkings")
	public ResponseEntity<List<ParkingDTO>> findAll() {
		List<Parking> parkingList = parkingService.findAll(); // Obtém a lista de estacionamentos
		List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList); // Converte para DTO
		return ResponseEntity.ok(result); // Retorna a lista de estacionamentos
	}

	// Endpoint para listar um estacionamento por ID
	@GetMapping(value = "/{id}")
	@Operation(summary = "Endpoint to list parking by id")
	public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
		Parking parking = parkingService.findById(id); // Busca o estacionamento pelo ID
		ParkingDTO result = parkingMapper.toParkingDTO(parking); // Converte para DTO
		return ResponseEntity.ok(result); // Retorna o estacionamento encontrado
	}

	// Endpoint para criar um novo estacionamento
	@PostMapping
	@Operation(summary = "Endpoint to create parking")
	public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
		Parking parkingCreate = parkingMapper.toParkingCreate(dto); // Converte DTO para entidade
		Parking parking = parkingService.create(parkingCreate); // Cria o estacionamento
		ParkingDTO result = parkingMapper.toParkingDTO(parking); // Converte para DTO
		return ResponseEntity.status(HttpStatus.CREATED).body(result); // Retorna o estacionamento criado
	}

	// Endpoint para deletar um estacionamento por ID
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Endpoint to delete parking by id")
	public ResponseEntity deleteById(@PathVariable String id) {
		parkingService.delete(id); // Deleta o estacionamento pelo ID
		return ResponseEntity.noContent().build(); // Retorna resposta sem conteúdo
	}

	// Endpoint para atualizar um estacionamento
	@PutMapping(value = "/{id}")
	@Operation(summary = "Endpoint to update parking")
	public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto) {
		Parking parkingCreate = parkingMapper.toParkingCreate(dto); // Converte DTO para entidade
		Parking parking = parkingService.update(id, parkingCreate); // Atualiza o estacionamento
		ParkingDTO result = parkingMapper.toParkingDTO(parking); // Converte para DTO
		return ResponseEntity.status(HttpStatus.OK).body(result); // Retorna o estacionamento atualizado
	}
	
	// Endpoint para realizar o checkout de um estacionamento
	@PostMapping(value = "/{id}")
	@Operation(summary = "Endpoint to exit parking")
	public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) {
		Parking parking = parkingService.checkOut(id); // Realiza o checkout do estacionamento
		return ResponseEntity.ok(parkingMapper.toParkingDTO(parking)); // Retorna o estacionamento após checkout
	}

}
