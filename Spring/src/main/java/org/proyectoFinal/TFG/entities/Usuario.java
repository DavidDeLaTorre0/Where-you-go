package org.proyectoFinal.TFG.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;	
	private String nombreUsu;
	
	@Column(unique = true)
	private String correo;
	private String contrasenia;
	private String telefono;

	//Usario-Vehiculo
	@OneToMany(mappedBy="vehiculoUsuario")
	private Collection <Vehiculo> vehiculos;
	
	//Usario-Anuncio-conductor
	@OneToMany(mappedBy="conductor")
	private Collection <Anuncio> anuncioConductor;
	
	//Usario-Anuncio-pasajero
	@ManyToMany(mappedBy="pasajeros")
	private Collection <Anuncio> anuncioPasajero;
	

	
	
	//===============================

	public Usuario() {
		this.vehiculos =  new ArrayList<Vehiculo>();
		this.anuncioConductor =  new ArrayList<Anuncio>();
		this.anuncioPasajero =  new ArrayList<Anuncio>();
	}




	public Usuario(String nombre, String nombreUsu, String correo, String contrasenia,
			String telefono) {
		super();
		this.nombre = nombre;
		this.nombreUsu = nombreUsu;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.telefono = telefono;
		this.vehiculos =  new ArrayList<Vehiculo>();
		this.anuncioConductor =  new ArrayList<Anuncio>();
		this.anuncioPasajero =  new ArrayList<Anuncio>();
	}



	//===================================

	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getNombreUsu() {
		return nombreUsu;
	}




	public void setNombreUsu(String nombreUsu) {
		this.nombreUsu = nombreUsu;
	}



	public String getCorreo() {
		return correo;
	}




	public void setCorreo(String correo) {
		this.correo = correo;
	}




	public String getContrasenia() {
		return contrasenia;
	}




	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}




	public String getTelefono() {
		return telefono;
	}




	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public Collection<Vehiculo> getVehiculos() {
		return vehiculos;
	}




	public void setVehiculos(Collection<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}




	public Collection<Anuncio> getUsuarioContuctor() {
		return anuncioConductor;
	}




	public void setUsuarioContuctor(Collection<Anuncio> anuncioConductor) {
		this.anuncioConductor = anuncioConductor;
	}




	public Collection<Anuncio> getUsuarioPasajero() {
		return anuncioPasajero;
	}




	public void setUsuarioPasajero(Collection<Anuncio> anuncioPasajero) {
		this.anuncioPasajero = anuncioPasajero;
	}
	
	//=======================
	
	
	
}
