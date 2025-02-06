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
public class Modelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	@ManyToOne
	private Marca marca;
	
	@OneToMany(mappedBy = "modelo")
	private Collection<TipoVehiculo> tipoVehiculos;

	//=========================


	public Modelo() {
		this.tipoVehiculos = new ArrayList<TipoVehiculo>();
	}


	public Modelo(String nombre, Marca marca) {
		super();
		this.nombre = nombre;
		this.marca = marca;
		this.tipoVehiculos = new ArrayList<TipoVehiculo>();
	}


	//==================================
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

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Collection<TipoVehiculo> getTipoVehiculo() {
		return tipoVehiculos;
	}

	public void setTipoVehiculo(Collection<TipoVehiculo> tipoVehiculo) {
		this.tipoVehiculos = tipoVehiculo;
	}
	
	//===========================
}
