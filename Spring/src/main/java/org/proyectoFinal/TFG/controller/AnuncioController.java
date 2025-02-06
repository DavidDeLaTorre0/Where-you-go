package org.proyectoFinal.TFG.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.Anuncio;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.entities.Vehiculo;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.service.AnuncioService;
import org.proyectoFinal.TFG.service.UsuarioService;
import org.proyectoFinal.TFG.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/anuncio")
public class AnuncioController {

	@Autowired
	private AnuncioService anuncioService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	@GetMapping("r")
	public String r(
			@RequestParam(value="origen",required = false) String origen,
			@RequestParam(value="destino",required = false) String destino,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@RequestParam(value = "fecha", required = false) LocalDate fecha,
			HttpSession s,
			ModelMap m) throws Exception , DangerException {
		try {
			
			if(origen == null || origen.equals("")) {
				throw new Exception("El origen no puede estar vacio");
			}
			if(destino == null || destino.equals("")) {
				throw new Exception("El destino no puede estar vacio");
			}
			if(fecha == null) {
				throw new Exception("La fecha es nula");
			}
			LocalDate hoy = LocalDate.now();
			if(hoy.isAfter(fecha)) {
				throw new Exception("La fecha introducida ya ha pasado");
			}
			
			List<Anuncio> anuncios = new ArrayList<>();
			anuncios = anuncioService.findByOrigenAndByDestinoAndByFechaOrderByFechaAsc(origen,destino,fecha);			
			if(anuncios.isEmpty()) {
			//si no hay anuncios hacia esa calle, que saque un viaje a la ciudad/ provincia de ese el pais con cualquier fecha
				anuncios = anuncioService.findByOrigenAndByDestinoOrderByFechaAsc(origen,destino);
				
				if(anuncios.isEmpty()) {
			//Sino no ha encontrado ningun viaje, que busque algun anuncio que contenga ese origen o ese destino 
					anuncios = anuncioService.findByOrigenAndByDestinoAndByFechaOrderByFechaAscContaining(origen,destino);
				}
			}
			

			if(anuncios.size() == 0) {
				String vacio = "Ningun";
				m.put("num_anuncios", vacio);
			}else {
			m.put("num_anuncios",anuncios.size());				
			}
			
			Boolean boo = true;
			
			if(usuarioService.existeSesion(s) == null) {
				boo = false;
			}
			
			
			m.put("boo", boo);	
			m.put("origen", origen);
			m.put("destino", destino);
			m.put("anuncios", anuncios);
//			m.put("anuncios", anuncioService.findAll());
			m.put("view", "anuncio/r");
			return "_t/frame";
		}catch(Exception e) {
			PRG.error(e.getMessage(),"/home");
		}
		return "redirect:/home";
	}
	
	@PostMapping("rAnuncio")
	public String rPost(
			@RequestParam(value="idAnuncio",required = false) Long idAnuncio,
			HttpSession s,
			ModelMap m) throws Exception,DangerException {
		
			
		try{
			
			Anuncio anuncio = anuncioService.getById(idAnuncio);
			LocalDate fecha = anuncio.getFecha();
			int numeroDia = fecha.getDayOfMonth();
			String dia = anuncioService.getDay(idAnuncio);
			String mes = anuncioService.getMes(idAnuncio);
			
			Boolean boo = true;
			
			if(usuarioService.existeSesion(s) == null) {
				boo = false;
			}
			
			
		m.put("boo", boo);	
		m.put("numeroDia",numeroDia);
		m.put("mes", mes);
		m.put("diaSem", dia);
		m.put("anuncio", anuncio);
		m.put("view", "anuncio/rAnuncio");
		
		return "_t/frame";
		
		
		}catch(Exception e) {
			PRG.error(e.getMessage(),"/home");
		}
		return "redirect:/home";
	}
	
	/*	RESEVAR 	*/
	
	@SuppressWarnings("null")
	@GetMapping("reservar")
	public String reservar(
			@RequestParam(value="idAnuncio",required = false) Long idAnuncio,
			HttpSession s,
			ModelMap m) throws DangerException {
		try{
			if(idAnuncio != null || idAnuncio != 0 ) {
				if(idAnuncio.equals("")) {
					throw new Exception("Error al recoger el id del viaje");
				}else {
					//Una vez pasadas las condiciones, saco el anuncio para sacar a su conductor
					//Y ver que no sea el mismo quien reserva su propio viaje
					Anuncio anuncio = anuncioService.getById(idAnuncio);
					Usuario usuConductor = new Usuario();
					usuConductor = anuncio.getConductor();
					Long idConductor = usuConductor.getId();
					if(s.getAttribute("usuario") != null) {
						Usuario usuario = new Usuario();
						usuario = usuarioService.getById(s);
						Long idPasajero = usuario.getId();
						if(idConductor !=  idPasajero) {
							
							anuncioService.agregarPasajero(idAnuncio , idPasajero);
							PRG.info("Felicidades, has reservado un viaje");
//							m.put("pasajero", usuarioService.getById(s));
//							m.put("view", "anuncio/");
//							return "_t/frame";
						}else {
							throw new Exception("No puedes reservar tu propio viaje!");
						}
					}else {
						throw new Exception ("Debes estar registrado para reservar el viaje");
					}
				}
			}else {
				throw new Exception("Error al recoger id del viaje");
			}

		}catch(Exception e) {
			PRG.error(e.getMessage(),"/home");
		}
		return "redirect:/home";
	}
	
	
	
	
	/*		C		*/
	@GetMapping("ruta")
	public String c(ModelMap m) {
		m.put("view", "anuncio/ruta");
		return "_t/frame";
	}
	
	@PostMapping("rutaPost")
	public String rutaPost(
			@RequestParam(value="origen",required = false) String origen,
			@RequestParam(value="destino",required = false) String destino,
			@RequestParam(value="tiempo",required = false) String tiempo,
			@RequestParam(value="distancia",required = false) String distancia,
			ModelMap m,
			HttpSession s) throws DangerException {
		
		try {
			if(origen == null || origen =="") {
				throw new Exception("El origen no puede estar vacio");
			}
			if(destino == null || destino =="") {
				throw new Exception("El destino no puede estar vacio");
			}
			if(tiempo == null || tiempo =="") {
				throw new Exception("Error al recoger el tiempo");
			}
			if(distancia == null || distancia =="") {
				throw new Exception("Error al recoger la distancia");
			}
			
			s.setAttribute("origen", origen);
			s.setAttribute("destino", destino);
			s.setAttribute("tiempo", tiempo);
			s.setAttribute("distancia", distancia);
			
			m.put("view", "anuncio/fecha");
			
			return "_t/frame";
			
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/anuncio/ruta");
		}
		return "redirect:/anuncio/ruta";
	}
	
	@PostMapping("fechaPost")
	public String fechaPost(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@RequestParam(value = "fecha", required = false) LocalDate fecha,
			ModelMap m,HttpSession s) throws DangerException {
			try {
				if(fecha == null) {
					throw new Exception("Error al recoger la fecha");
				}
				LocalDate hoy = LocalDate.now();
				if(hoy.isAfter(fecha)) {
					throw new Exception("La fecha introducida ya ha pasado");
				}
				//formato fecha 2022-12-15
				s.setAttribute("fecha", fecha);
				m.put("view", "anuncio/hora");
				return "_t/frame";
			}catch(Exception e) {
				PRG.error(e.getMessage(),"/anuncio/ruta");
			}

		return "redirect:/anuncio/ruta";
	}
	
	@PostMapping("horaPost")
	public String horaPost(
	@RequestParam(value = "hora", required = false) LocalTime hora,
	ModelMap m,HttpSession s) throws DangerException {
		
		try {/* 06:51 */
			if(hora == null) {
				throw new Exception("Error al recoger la hora");
			}
//			LocalTime ahora = LocalTime.now();
//			LocalDate hoy = LocalDate.now();
//			if(hoy) {
//				if(ahora.isAfter(hora)) {
//					throw new Exception("La hora introducida ya ha pasado");
//				}
//			}
			s.setAttribute("hora", hora);
			m.put("view", "anuncio/plaza");
			return "_t/frame";
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/anuncio/ruta");
		}
		return "redirect:/anuncio/ruta";
	}
	
	@PostMapping("plazaPost")
	public String plazaPost(
			@RequestParam(value = "plaza", required = false) int plaza,
			ModelMap m,
			HttpSession s
			) throws DangerException {

		try {
			if(plaza <= 0 || plaza >= 5) {
				throw new Exception("Error el numero de plazas debe estar entre el rango 1 a 4");
			}
			
			s.setAttribute("plaza", plaza);	
			m.put("vehiculos",vehiculoService.findByVehiculoUsuarioId(s));
			m.put("view", "anuncio/vehiculo");
			return "_t/frame";
		}
			catch(Exception e) {
				PRG.error(e.getMessage(),"/anuncio/ruta");
			}
		return "redirect:/anuncio/ruta";
	}
	
	
	@PostMapping("vehiculoPost")
	public String vehiculoPost(
			@RequestParam(value = "idVehiculo", required = false) Long idVehiculo,
			ModelMap m,
			HttpSession s
			) throws DangerException {
			try {
				Vehiculo vehiculo = new Vehiculo();
				 vehiculo = vehiculoService.getById(idVehiculo);
				 s.setAttribute("vehiculo_anuncio", vehiculo);
				 
				String distancia = (String) s.getAttribute("distancia");
				m.put("precio_recomendado", anuncioService.getPrecioRecomendado(distancia));
				m.put("view", "anuncio/precio");
				return "_t/frame";
			}catch(Exception e) {
				PRG.error(e.getMessage(),"/anuncio/ruta");
			}
			return "redirect:/anuncio/ruta";
	}
	
	@PostMapping("precioPost")
	public String precioPost(
			@RequestParam(value = "precio", required = false) int precio,
			ModelMap m,
			HttpSession s) throws DangerException {
		try {
			if(precio <= 0) {
				throw new Exception ("El precio por persona no puede ser menor a 0");
			}
			if(precio >= 100) {
				throw new Exception ("El precio por persona no puede ser mayor a 100");
			}
			String origen,destino,tiempo;
			LocalTime hora,hora_llegada;
			int plaza;
			LocalDate fecha;
			Vehiculo vehiculo = new Vehiculo();
			Usuario usuario = new Usuario();

			
			origen = (String) s.getAttribute("origen");
			destino = (String) s.getAttribute("destino");
			tiempo = (String) s.getAttribute("tiempo");
			fecha = (LocalDate) s.getAttribute("fecha");
			hora = (LocalTime) s.getAttribute("hora");
			
			hora_llegada = anuncioService.getHoraLlegada(hora,tiempo);
			
			plaza = (int) s.getAttribute("plaza");
			vehiculo = (Vehiculo) s.getAttribute("vehiculo_anuncio");
			usuario = (Usuario) s.getAttribute("usuario");
			
			if(usuario == null) {
				throw new Exception ("Sesion inactiva debes iniciar sesion");
			}
			anuncioService.save(plaza,fecha,hora,precio, hora_llegada,usuario,vehiculo,origen,destino);
			PRG.info("Anuncio guardado con exito","/home");
		}
		catch(Exception e) {
			PRG.error(e.getMessage(),"/anuncio/ruta");
		}
		return "redirect:/anuncio/ruta";
	}
	


	
}
