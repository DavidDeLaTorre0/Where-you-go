package org.proyectoFinal.TFG.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;

	
	//cada marca tendrá una coleccion de modelos
	@OneToMany(mappedBy = "marca")
	private Collection<Modelo> modelos;

	//====================================================
	public Marca() {
		this.modelos = new ArrayList<Modelo>();
	}



	public Marca(String nombre) {
		super();
		this.nombre = nombre;
		this.modelos = new ArrayList<Modelo>();
	}



	//========================================================
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


	public Collection<Modelo> getModelos() {
		return modelos;
	}


	public void setModelos(Collection<Modelo> modelos) {
		this.modelos = modelos;
	}
	

}
