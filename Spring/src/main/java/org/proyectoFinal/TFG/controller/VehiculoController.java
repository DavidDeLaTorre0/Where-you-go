package org.proyectoFinal.TFG.controller;


import javax.servlet.http.HttpSession;

import org.proyectoFinal.TFG.entities.TipoVehiculo;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.exception.DangerException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.service.MarcaService;
import org.proyectoFinal.TFG.service.ModeloService;
import org.proyectoFinal.TFG.service.TipoVehiculoService;
import org.proyectoFinal.TFG.service.UsuarioService;
import org.proyectoFinal.TFG.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoController {
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MarcaService marcaService;
	
	@Autowired
	private ModeloService modeloService;
	
	@Autowired
	private TipoVehiculoService tipoVehiculoService;
	
	
	
	@GetMapping("c")
		public String marca(ModelMap m) {
		m.put("marcas", marcaService.findAll());
		m.put("view", "vehiculo/marca");
		return "_t/frame";
	}
	
	
	@PostMapping("modelo")
	public String modelo(ModelMap m, HttpSession s,
			@RequestParam(value="idMarca",required = false) Long idMarca
			) {
		s.setAttribute("idMarca", idMarca);
		
		m.put("modelos", modeloService.getByMarca(idMarca));
		m.put("view", "vehiculo/modelo");
		return "_t/frame";
	}
	
	
	@PostMapping("tipoVehiculo")
	public String tipoVehiculo(ModelMap m, HttpSession s,
			@RequestParam(value="idModelo",required = false) Long idModelo
			) {
		s.setAttribute("idModelo", idModelo);
	//Si lo ponemos en harcode, ¿como muevo la info?
		m.put("tipoVehiculos", tipoVehiculoService.getByModelo(idModelo));
		m.put("view", "vehiculo/tipoVehiculo");
		return "_t/frame";
	}
	
	@PostMapping("matricula")
	public String matricula(ModelMap m,HttpSession s,
			@RequestParam(value="idTipoVehiculo",required = false) Long idTipoVehiculo
			) {
		s.setAttribute("idTipoVehiculo", idTipoVehiculo);
		//Si lo ponemos en harcode, ¿como muevo la info?
		m.put("view", "vehiculo/matricula");
		return "_t/frame";
	}
	
	@PostMapping("color")
	public String color(ModelMap m,HttpSession s,
			@RequestParam(value="matricula",required = false) String matricula
			) throws DangerException {
		String path = "";
		try {
			matricula = matricula.toUpperCase();
			
			if (matricula == null || matricula.equals("")) {
				PRG.error("La matricula no puede estar vacía, vuelve a empezar", "/vehiculo/c");
			}
			if (vehiculoService.existeMatricula(matricula)) {
				PRG.error("La matricula ya existe, vuelve a meter el vehiculo","/vehiculo/c" );
			}
			s.setAttribute("matricula", matricula);
			//Si lo ponemos en harcode, ¿como muevo la info?
			m.put("view", "vehiculo/color");
			path = "_t/frame";
		}catch(Exception e) {
			
			PRG.error(e.getMessage(), "/vehiculo/c");
		}
		return path;
	}

	/// ERROR AQUI
	@PostMapping("cPost")
	public String cPost(
			@RequestParam(value = "color", required = false) String color,
			HttpSession s) throws DangerException {
		
		try {
			if (color == null || color.equals("")) {
				throw new Exception("El color no puede ser vacío");
			}
			
//				da error			
//			Long idTipoVehiculo = (Long) s.getAttribute("idTipoVehiculo");
//			
//			TipoVehiculo tipoVehiculo = new TipoVehiculo();
//			tipoVehiculo = tipoVehiculoService.getById(idTipoVehiculo);
			
			
			Long idTipoVehiculo =  (Long) s.getAttribute("idTipoVehiculo");
			
			String matricula = (String) s.getAttribute("matricula");
			
			Usuario usuario = new Usuario();
			usuario = (Usuario) s.getAttribute("usuario");
			
			vehiculoService.save(color,matricula,idTipoVehiculo,usuario);

		} catch (Exception e) {
			PRG.error(e.getMessage(), "/vehiculo/c");
		}
		
		return "redirect:/menu";
	}
	

	/*
	 * 	R 	- 	VEHICULO 
	 * 
	 */
	
	@GetMapping("r")
		public String r(ModelMap m,HttpSession s) {
		m.put("vehiculos", vehiculoService.findByVehiculoUsuarioId(s));
		m.put("view", "vehiculo/r");
		return "_t/frame";
	}
	
	
	
	/*
	 *  D	-	VEHICULO
	 * 
	 */
	
	@GetMapping("d")
	public String d(ModelMap m,HttpSession s) {
	m.put("vehiculos", vehiculoService.findByVehiculoUsuarioId(s));
	m.put("view", "vehiculo/d");
	return "_t/frame";
}
	
	@PostMapping("dPost")
	public void dPost(
			@RequestParam(value="idVehiculo",required = false) Long idVehiculo
			) throws DangerException {
		
		try {
			if(idVehiculo == 0 || idVehiculo == null) {
				PRG.error("El id del vehiculo no puede ser 0 o estar vacio", "vehiculo/d");
			}
				vehiculoService.deleteById(idVehiculo);
			
		}catch(Exception e) {
			PRG.error(e.getMessage(), "/menu");
		}
	}
	
}
