package org.proyectoFinal.TFG.controller;

import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.Tarjeta;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.service.TarjetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
@RequestMapping("/tarjeta")
public class TarjetaController {

	
	@Autowired
	private TarjetaService tarjetaService;	
	
	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "tarjeta/c");
		return "_t/frame";
	}
	
	@PostMapping("cPost")
	public void cPost(
			@RequestParam(value="numeroTarjeta",required = false) String numeroTarjeta,
			@RequestParam(value="nombre",required = false) String nombre,
			@RequestParam(value="mes",required = false) String mes, 
			@RequestParam(value="anio",required = false) String anio,
			@RequestParam(value="cvv",required = false) String cvv,
			HttpSession s)throws DangerException {
		
		try {
			if(numeroTarjeta == "" || numeroTarjeta.equals("")) {
				throw new Exception("Hay que introducir un numero de la tarjeta");
			}
			if(nombre == "" || nombre.equals("")) {
				throw new Exception("El campo del titular NO debe estar vacio");
			}
			if(mes == "" || mes.equals("")) {
				throw new Exception("Debe seleccionar un mes");
			}
			if(anio == "" || anio.equals("")) {
				throw new Exception("Debe seleccionar un año");
			}
			if(cvv == "" || cvv.equals("")) {
				throw new Exception("Debe introducir el cvv");
			}
			nombre = nombre.toUpperCase();
			tarjetaService.save(numeroTarjeta,nombre,mes,anio,cvv,s);
			PRG.info("La tarjeta se ha guardado satisfactoriamente","/menu");
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/tarjeta/c");
		}
		
	}
	
	@GetMapping("r")
	public String r(ModelMap m, HttpSession s) {
		Usuario usuario = new Usuario();
		usuario = (Usuario) s.getAttribute("usuario");
		Long id= usuario.getId();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta = tarjetaService.getByUsuarioId(id);
		m.put("tarjeta", tarjeta);
		m.put("view", "tarjeta/r");
		return "_t/frame";
	}
	
	@GetMapping("u")
	public String u(ModelMap m, HttpSession s) {
		Usuario usuario = new Usuario();
		usuario = (Usuario) s.getAttribute("usuario");
		Long id= usuario.getId();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta = tarjetaService.getByUsuarioId(id);
		
		String[] mes = new String[12];
		for(int i=0; i<=11; i++) {
			mes[i]= ""+(i + 1)+ "";
		}
		String[] anio= new String[9];
		for(int i=0; i<9; i++) {
			anio[i]= "" +(22 + i)+ "";
		}
		m.put("meses", mes);
		m.put("anios", anio);
		m.put("tarjeta", tarjeta);
		m.put("view", "tarjeta/u");
		return "_t/frame";
	}
	
	@PostMapping("uPost")
	public void uPost(
			@RequestParam(value="numeroTarjeta",required = false) String numeroTarjeta,
			@RequestParam(value="nombre",required = false) String nombre,
			@RequestParam(value="mes",required = false) String mes, 
			@RequestParam(value="anio",required = false) String anio,
			@RequestParam(value="cvv",required = false) String cvv,
			HttpSession s)throws DangerException {
		
		try {
			if(numeroTarjeta == "" || numeroTarjeta.equals("")) {
				throw new Exception("Hay que introducir un numero de la tarjeta");
			}
			if(nombre == "" || nombre.equals("")) {
				throw new Exception("El campo del titular NO debe estar vacio");
			}
			if(mes == "" || mes.equals("")) {
				throw new Exception("Debe seleccionar un mes");
			}
			if(anio == "" || anio.equals("")) {
				throw new Exception("Debe seleccionar un año");
			}
			if(cvv == "" || cvv.equals("") ) {
				throw new Exception("Debe introducir el cvv");
			}
			nombre = nombre.toUpperCase();
			tarjetaService.update(numeroTarjeta,nombre,mes,anio,cvv,s);
			
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/tarjeta/u");
		}
		
	}
}
