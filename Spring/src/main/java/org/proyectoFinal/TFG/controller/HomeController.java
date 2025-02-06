package org.proyectoFinal.TFG.controller;

import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.Tarjeta;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.service.TarjetaService;
import org.proyectoFinal.TFG.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private VehiculoService vehiculoService;


	@GetMapping("/info")
	public String info(
			HttpSession s,
			ModelMap m
			) {
		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}
	


	@GetMapping("/")
	public String index(ModelMap m,HttpSession s) {
		s.invalidate();
		m.put("view", "home/index");
		return "_t/frame";
	}
	
	/*
	 * HOME CUANDO SE HAYA INCIADO SESION
	 */
	
	@GetMapping("/home")
	public String prueba(ModelMap m, HttpSession s) throws Exception {
		//APor si intentan meter la ruta sin estar registrado o sin hacer iniciado session
		if(s.getAttribute("usuario") != null){
			
			Usuario usuario = new Usuario();
			usuario =(Usuario) s.getAttribute("usuario");
			Long id  = usuario.getId();
			
			int num = 0;
				if(tarjetaService.getByUsuarioId(id)!=null) {
					num ++;
					if(vehiculoService.findByVehiculoUsuarioId(s)!=null) {
						num ++;
					}else {
					
					}
				}else {
					
				}
			
			
			
			m.put("num", num);
			m.put("view", "home/home");
			return "_t/frame";
		}else {
			PRG.error("No tienes acceso, Inicia Sesion","/");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/menu")
	public String menUsuario(ModelMap m,HttpSession s) throws DangerException {

		//APor si intentan meter la ruta sin estar registrado o sin hacer iniciado session
		if(s.getAttribute("usuario") != null){
			//para hacer visible o no el campo de la tarjeta/c
			Usuario usuario = new Usuario();
			usuario = (Usuario) s.getAttribute("usuario");
			Long id = usuario.getId();
			
			Tarjeta tarjeta = new Tarjeta();
			tarjeta = tarjetaService.getByUsuarioId(id);
			
			m.put("tarjeta",tarjeta);
			m.put("view", "home/menu");
			return  "_t/frame";
		}else {
			PRG.error("No tienes acceso, Inicia Sesion","/");
		}
		
		return "redirect:/";
		
		
	}
	
	
}
