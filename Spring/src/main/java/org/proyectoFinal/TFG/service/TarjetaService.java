package org.proyectoFinal.TFG.service;

import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.Tarjeta;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaService {

	@Autowired
	private TarjetaRepository tarjetaRepository;

	public void save(String numeroTarjeta, String nombre, String mes, String anio, String cvv, HttpSession s)throws DangerException {
		
		try {
			
			Usuario usuario = new Usuario();
			usuario = (Usuario) s.getAttribute("usuario");
			if(usuario == null) {
				throw new Exception("Error usuario no identificado");
			}
			tarjetaRepository.save(new Tarjeta(nombre,numeroTarjeta,mes,anio,cvv,usuario));
			
		}catch(Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		
	}

	public Tarjeta getByUsuarioId(Long id) {
		
		return tarjetaRepository.getByUsuarioId(id);
	}

	public void update(String numeroTarjeta, String nombre, String mes, String anio, String cvv, HttpSession s) throws Exception {
		
			Usuario usuario = new Usuario();
			usuario = (Usuario) s.getAttribute("usuario");
			Long id = usuario.getId();
			
			Tarjeta tarjeta = this.getByUsuarioId(id);
				
			tarjeta.setNumeroTarjeta(numeroTarjeta);
			tarjeta.setTitularTarjeta(nombre);
			tarjeta.setMes(mes);
			tarjeta.setAnio(anio);
			tarjeta.setCvv(cvv);
			tarjeta.setUsuario(usuario);
			tarjetaRepository.saveAndFlush(tarjeta);
			
			PRG.info("Exito al actualizar la tarjeta", "/tarjeta/u");
		
	}
}
