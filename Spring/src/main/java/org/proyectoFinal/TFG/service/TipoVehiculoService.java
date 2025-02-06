package org.proyectoFinal.TFG.service;

import java.util.List;

import org.proyectoFinal.TFG.entities.Modelo;
import org.proyectoFinal.TFG.entities.TipoVehiculo;
import org.proyectoFinal.TFG.repository.TipoVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVehiculoService {

	@Autowired
	private ModeloService modeloService;

	@Autowired
	private TipoVehiculoRepository tipoVehiculoRepository;
	
	//Quiero recoger  los tipos de vehiculos  con los id del mismo modelos
	public List<TipoVehiculo> getByModelo(Long idModelo) {
		
		Modelo modelo = new Modelo();
		modelo = modeloService.getById(idModelo);
		return tipoVehiculoRepository.getByModelo(modelo);
	}

	public TipoVehiculo getById(Long idTipoVehiculo) {
		// TODO Auto-generated method stub
		return tipoVehiculoRepository.getById(idTipoVehiculo);
	}
	


}
