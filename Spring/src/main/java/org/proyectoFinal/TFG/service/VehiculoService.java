package org.proyectoFinal.TFG.service;

import java.util.Collection;

import javax.servlet.http.HttpSession;


import org.proyectoFinal.TFG.entities.TipoVehiculo;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.entities.Vehiculo;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.InfoException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.repository.TipoVehiculoRepository;
import org.proyectoFinal.TFG.repository.VehiculoRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehiculoService {
	
	@Autowired
	private VehiculoRepositoy vehiculoRepository;
	
	@Autowired
	private TipoVehiculoRepository tipoVehiculoRepository;
	
	public void save(String color, String matricula, Long idTipoVehiculo, Usuario usu) throws DangerException {
		
		try {
			
			TipoVehiculo tipoVehiculo = new TipoVehiculo();
			tipoVehiculo = tipoVehiculoRepository.getById(idTipoVehiculo);
			
			vehiculoRepository.save(new Vehiculo(color,matricula,tipoVehiculo,usu));
			PRG.info("Vehiculo creado con exito", "/menu");
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/vehiculo/c");
		}
		
	}
	
	
	
	public boolean existeMatricula(String matricula) {
		boolean sol = false;
		//RECOJO TODOS LOS VEHICULOS Y SI EXISTE UNO QUE SEA SOL=TRUE
		for (Vehiculo vehiculo : vehiculoRepository.findAll()) {
			if (vehiculo.getMatricula().equals(matricula)) {
				sol = true;
			}
		}
		return sol;
	}
	
//	//Recojo el vehiculo que tenga ese usuario
//	public Vehiculo getVehiculoUsuario(HttpSession s) {
//		Usuario usuario = new Usuario();
//		usuario = (Usuario) s.getAttribute("usuario");
//		return vehiculoRepository.getByVehiculoUsuario(usuario);
//	}
//	

	public Collection<Vehiculo> findByVehiculoUsuarioId(HttpSession s) {
		Usuario usuario = new Usuario();
		usuario = (Usuario) s.getAttribute("usuario");
		Long id = usuario.getId();
		return vehiculoRepository.findByVehiculoUsuarioId(id);
	}

	/*
	 * D	-	vehiculo
	 */
	public void deleteById(Long id) throws InfoException, Exception  {
			vehiculoRepository.deleteById(id);
			PRG.info("Exito al borrar vehiculo", "/menu");
	}



	public Vehiculo getById(Long idVehiculo) {
		return vehiculoRepository.getById(idVehiculo);
		
	}
}
