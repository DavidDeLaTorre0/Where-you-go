package org.proyectoFinal.TFG.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.entities.Vehiculo;
import org.proyectoFinal.TFG.exception.InfoException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	VehiculoService vehiculoService;

	/*
	 * 			-- LOGIN --
	 */
	
	//PARA VER SI EXISTE ESE USUARIO Y LA CONTRASEÑA ESTA BEIN
		public void inicioSesion(String email, String passwrd, HttpSession s) throws Exception {
			try {
				
				Usuario usuario = new Usuario();
				usuario= usuarioRepository.getByCorreo(email);
				
				if(usuario == null) {
					throw new Exception("No tienes ningun usuario registrado");
				}
				
				if (new BCryptPasswordEncoder().matches(passwrd, usuario.getContrasenia()) && (email.equals(usuario.getCorreo()))) {
					s.setAttribute("usuario", usuario);
					s.setAttribute("password", passwrd);
					PRG.info("Inicio exitoso","/home");
				}
				else {
					throw new Exception("Usuario o Contraseña incorrecta");
				}
				if(email != usuario.getCorreo()) {
					throw new Exception("Usuario o Contraseña incorrecta");
				}
				
			}catch(Exception e) {
				PRG.error(e.getMessage(), "/usuario/login");
			}
		}
	
	
	/*
	 * 	C	-	USUARIO
	 */
	public void save(String nombre, String nombreUsuario, String email, String passwrd, String tlf) throws Exception {
		try {
			
			//Encriptamos la contraseña
			String pwd =  (new BCryptPasswordEncoder()).encode(passwrd);
			//CREAMOS AL USUARIO SIN FOTO DE PERFIL
			usuarioRepository.save(new Usuario(nombre,nombreUsuario,email,pwd,tlf));
			
		}catch(Exception e) {
			//EL ERROR VIENE DE LA CLASE USUARIO, YA QUE HE PUESTO QUE EL EMAIL COMO CAMPO UNICO
			throw new Exception("El email "+email+" ya existe");
		}
	}
	
	
	public Usuario getById(HttpSession s)throws Exception {

		try {
			Usuario usuario = new Usuario();
			Long idUsuario = null;
			usuario = (Usuario) s.getAttribute("usuario");
			idUsuario = usuario.getId();
			return usuarioRepository.getById(idUsuario);
		}
		catch(Exception e) {
			throw new Exception("Ha habido un problema al sacar su usuario");
		}
	}
	
	
	/*
	 * r  --   Aqui seria el r del ususario que esta en el menu de usuario
	 */

	

	
	/*
			D - USUARIO		PARA BORRAR EL USUARIO TAMBIEN TENGO QUE BORRAR SUS COCHES, ANUNCIOS, TARJETAS ???
	*/
	public void delete(HttpSession s) throws Exception{
		try {
			Usuario usuario = new Usuario();
			Long id = null;
			usuario = (Usuario) s.getAttribute("usuario");
			id = usuario.getId();
			
			//RECOJO LOS VEHICULOS DE ESE USUARIO Y LOS BORRO
			Collection<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
			vehiculos = vehiculoService.findByVehiculoUsuarioId(s);
			
			for (Vehiculo vehiculo : vehiculos) {
				if (vehiculo.getId()!=0) {
					vehiculoService.deleteById(id);
				}
			}
			
			//LUEGO BORRO ESE USUARIO
			usuarioRepository.deleteById(id);
//			s.invalidate();
			PRG.info("Exito al borrar usuario", "/");
		}catch(Exception e) {
			throw new Exception("Error al borrar al usuario");
		}
	}
	
	/*
	 *		U - USUARIO
	 */
	public void update(String nombre, String nombreUsuario, String email, String passwrd, String tlf, HttpSession s) throws Exception {

			Usuario usuario = (Usuario) s.getAttribute("usuario");
			
			usuario.setNombre(nombre);
			usuario.setNombreUsu(nombreUsuario);
			usuario.setCorreo(email);
			usuario.setTelefono(tlf);
			
			//Encriptamos la contraseña
			String pwd =  (new BCryptPasswordEncoder()).encode(passwrd);
			
			usuario.setContrasenia(pwd);
			usuarioRepository.saveAndFlush(usuario);
			
			PRG.info("Exito al actualizar los datos del usuario","usuario/u");



		
	}


	public Usuario existeSesion(HttpSession s) {
		
		Usuario usuario = new Usuario();
		usuario = (Usuario) s.getAttribute("usuario");
		if(usuario !=null) {
			Long id = usuario.getId();
			return usuarioRepository.getById(id);
		}
		
		return usuario;
	}
	
}
