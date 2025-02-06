package org.proyectoFinal.TFG.service;

import java.util.List;

import org.proyectoFinal.TFG.entities.Marca;
import org.proyectoFinal.TFG.entities.Modelo;
import org.proyectoFinal.TFG.repository.MarcaRepository;
import org.proyectoFinal.TFG.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {


	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private MarcaService marcaService;

	public List<Modelo> findAll() {
		return modeloRepository.findAll();
	}
	
	
	//Quiero recoger los modelos con los id de la misma marca
	public List<Modelo> getByMarca(Long idMarca) {
		
		Marca marca = new Marca();
		marca = marcaService.getById(idMarca);
		
//		Modelo modelo = new Modelo();
//		modelo.setMarca(marca);
		
		return modeloRepository.getByMarca(marca);
	}
	
	//Si yo quiero encontrar el modelo de una marca, getByMarca?
	
//	public Modelo getByMarca(Long idMarca) {
//		return modeloRepository.getById(idMarca);
//	}
//	
	public Modelo getById(Long idModelo) {
		return modeloRepository.getById(idModelo);
	}
	//Si quiero saber los id de cada uno en la vista tengo que llamar al objeto.id en el value así se cual es



}
