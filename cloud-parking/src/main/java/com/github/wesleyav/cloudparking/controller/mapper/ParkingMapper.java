package com.github.wesleyav.cloudparking.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.wesleyav.cloudparking.controller.dto.ParkingCreateDTO;
import com.github.wesleyav.cloudparking.controller.dto.ParkingDTO;
import com.github.wesleyav.cloudparking.model.Parking;

@Component
public class ParkingMapper {

	// Instância estática do ModelMapper para conversão de objetos
	private static final ModelMapper MODEL_MAPPER = new ModelMapper();

	// Método para converter um objeto Parking em ParkingDTO
	public ParkingDTO toParkingDTO(Parking parking) {
		return MODEL_MAPPER.map(parking, ParkingDTO.class);
	}

	// Método para converter uma lista de objetos Parking em uma lista de ParkingDTO
	public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList) {
		return parkingList.stream().map(this::toParkingDTO).collect(Collectors.toList());
	}

	// Método para converter um objeto ParkingDTO em Parking
	public Parking toParking(ParkingDTO dto) {
		return MODEL_MAPPER.map(dto, Parking.class);
	}

	// Método para converter um objeto ParkingCreateDTO em Parking
	public Parking toParkingCreate(ParkingCreateDTO dto) {		
		return MODEL_MAPPER.map(dto, Parking.class);
	}

}
