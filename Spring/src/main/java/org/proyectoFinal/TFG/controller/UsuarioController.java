package org.proyectoFinal.TFG.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.Anuncio;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.InfoException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.service.AnuncioService;
import org.proyectoFinal.TFG.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {


	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AnuncioService anuncioService;
	/*
	 * 			HOME USUARIO
	 */

	
	/*
	 * CUANDO PULSES EN USUARIO MUESTRA EL MENU DE ESTE
	 */
	

	
	/*
	 * 			LOGIN 
	 */
	@GetMapping("login")
	public String login(ModelMap m) {
		m.put("view", "usuario/login");
		return "_t/frame";
	}
	
	@PostMapping("loginPost")
	public void loginPost(
			@RequestParam(value="email",required = false) String email, 
			@RequestParam(value="pwd",required = false) String pwd, 
			HttpSession s)throws DangerException {
		

		try {
			if(email == "" || email.equals("")) {
				throw new Exception("El email no puede estar vacio");
			}
			if(pwd == "" || pwd.equals("")) {
				throw new Exception("La contraseña no puede estar vacia");
			}
			usuarioService.inicioSesion(email, pwd, s);
//			PRG.info("Inicio exitoso","/home");  SI LO PONGO AQUI ME SALTA EL ERROR DEL SERVICE, AUNQUE TODO ESTÉ BIEN
		} catch (Exception e) {
			PRG.error( e.getMessage(), "/usuario/login");
		}
	}
	
	
	
	/**
	 *  C  - USUARIO
	 */
	
	
	@GetMapping("logup")
	public String logup(ModelMap m) {
		m.put("view", "usuario/logup");
		return "_t/frame";
	}

	@PostMapping("logupPost")
	public void logupPost(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "nombreUsu", required = false) String nombreUsuario,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "tlf", required = false) String tlf,
			@RequestParam(value = "pwd", required = false) String pwd,
			@RequestParam(value = "confPwd", required = false) String confPwd			
			) throws DangerException, InfoException {
		try {
			if (nombre == null || nombre.equals("")) {
				throw new Exception("El nombre no puede ser nulo");
			}
			if (nombreUsuario == null || nombreUsuario.equals("")) {
				throw new Exception("El nombre de usuario no puede ser nulo");
			}
			if (email == null || email.equals("")) {
				throw new Exception("La mail no puede ser nulo");
			}
			if (pwd == null || pwd.equals("")) {
				throw new Exception("La confirmacion de contraseña no puede ser nulo");
			}
			if (confPwd == null || confPwd.equals("")) {
				throw new Exception("El nombre no puede ser nulo");
			}
//			if ( !pwd.equals(confPwd)) {
//				throw new Exception("Las contraseñas no coinciden");
//			}
			if (tlf == null || tlf.equals("")) {
				throw new Exception("El telefono no puede ser nulo");
			}
			usuarioService.save(nombre,nombreUsuario,email,pwd,tlf);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/usuario/logup");
		}
		
		//Infoma de que ya estas registrado y vas al login
		PRG.info("El usuario se ha creado con exito","/usuario/login");
		//usuario r seria la vista cuando ya este el usuario registrado
	}
	
	
	
	
	/*
	 * 		R  - USUARIO
	 */
	@GetMapping("r")
	public String r(ModelMap m, HttpSession s) throws DangerException {
		String path = "";
		try {

		m.put("usuario", usuarioService.getById(s));
		m.put("contrasenia", s.getAttribute("password"));
		m.put("view", "usuario/r");
		path = "_t/frame";
		
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/");
		}
		return path;
	}
	
	
	/*
	 * 		U  -   USUARIO
	 */
	
	@GetMapping("u")
	public String U(ModelMap m, HttpSession s) throws DangerException {
		String path = "";
		try {

		m.put("usuario", usuarioService.getById(s));
		m.put("contrasenia", s.getAttribute("password"));
		m.put("view", "usuario/u");
		path = "_t/frame";
		
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/");
		}
		return path;
	}

	@PostMapping("/uPost")
	public void uPost(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "nombreUsu", required = false) String nombreUsuario,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "pwd", required = false) String pwd,
//			@RequestParam(value = "confPwd", required = false) String confPwd,
			@RequestParam(value = "tlf", required = false) String tlf,
			HttpSession s
			) throws DangerException, InfoException {
		try {
			if (nombre == null || nombre.equals("")) {
				throw new Exception("El nombre no puede ser nulo");
			}
			if (nombreUsuario == null || nombreUsuario.equals("")) {
				throw new Exception("El nombre de usuario no puede ser nulo");
			}
			if (email == null || email.equals("")) {
				throw new Exception("El email no puede ser nulo");
			}
			if (pwd == null || pwd.equals("")) {
				throw new Exception("La contraseña no puede ser nulo");
			}
//			if (confPwd == null || confPwd.equals("")) {
//				throw new Exception("La confirmacion de contraseña no puede ser nulo");
//			}
			if (tlf == null || tlf.equals("")) {
				throw new Exception("El telefono no puede ser nulo");
			}
//			if (pwd != confPwd) {
//				throw new Exception("Las contraseñas no coinciden");
//			}
			usuarioService.update(nombre,nombreUsuario,email,pwd,tlf,s);
		
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/menu");
		}
		//usuario r seria la vista cuando ya este el usuario registrado
		PRG.info("Exito al actualizar usuario", "/menu");
	}
		
	
	
	/*  R  -  ANUNCIO_USUARIO  */
	
	@GetMapping("viaje")
	public String viaje(
			HttpSession s,
			ModelMap m
			) throws Exception {
		
		Usuario usuario = new Usuario();
		usuario = usuarioService.getById(s);
		List<Anuncio> anunciosPropios = new ArrayList<Anuncio>();
		anunciosPropios = anuncioService.getByConductorId(usuario);
			if(anunciosPropios.isEmpty()|| anunciosPropios.size()==0) {
				anunciosPropios = null;
			}
		m.put("anuncios", anunciosPropios);
		m.put("view", "usuario/viajePropio");
		return "_t/frame";
	}
	
	/*  D  -  ANUNCIO_USUARIO  */
	@PostMapping("viajePost")
	public String viajePost(
			@RequestParam(value = "idAnuncio", required = false) Long idAnuncio,
			HttpSession s
			) throws DangerException {
		
		try {
			
			anuncioService.deleteById(idAnuncio);
			
			PRG.info("Exito al borrar el anuncio","/usuario/viaje");
			
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/usuario/viaje");
		}
		return "redirect:/usuario/viaje";
	}
	
	
	/*
	 *  D  -  USUARIO
	 */
	
	@GetMapping("d")
	public String d(ModelMap m) {
		m.put("view", "usuario/d");
		return "_t/frame";
	}

	@PostMapping("dPost")
	public void dPost(HttpSession s) throws DangerException {
		
		try {
			usuarioService.delete(s);
			
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/menu");
		}
	}
	
	@GetMapping("logout")
	public void logout(
			HttpSession s) throws InfoException {
		s.invalidate();
		
		PRG.info("Ha cerrado su sesion","/");
	}
	
	
	
	
}


