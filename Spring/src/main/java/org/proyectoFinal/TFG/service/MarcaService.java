package org.proyectoFinal.TFG.service;

import java.util.List;


import org.proyectoFinal.TFG.entities.Marca;
import org.proyectoFinal.TFG.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

	@Autowired
	private MarcaRepository marcaRepository;

	public List<Marca> findAll() {
		return marcaRepository.findAll();
	}
	
	public Marca getById(Long idMarca) {
		return marcaRepository.getById(idMarca);
	}
	
}
