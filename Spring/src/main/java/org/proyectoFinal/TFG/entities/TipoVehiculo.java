package org.proyectoFinal.TFG.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class TipoVehiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String nombre;
	
	@ManyToOne
	private Modelo modelo;
	
	@OneToMany(mappedBy = "tipoVehiculo")
	private Collection<Vehiculo> vehiculos;
	
	//======================================

	public TipoVehiculo() {
		this.vehiculos = new ArrayList<Vehiculo>();
	}


	
	public TipoVehiculo(String nombre, Modelo modelo) {
		super();
		this.nombre = nombre;
		this.modelo = modelo;
		this.vehiculos = new ArrayList<Vehiculo>();
	}



	//====================================
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

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Collection<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(Collection<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	} 
	
	//================================================

}
