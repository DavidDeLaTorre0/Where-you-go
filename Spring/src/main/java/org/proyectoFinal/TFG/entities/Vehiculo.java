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
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String color;
	
	@Column(unique = true)
	private String matricula;

	@ManyToOne
	private TipoVehiculo tipoVehiculo;
	
	@ManyToOne
	private Usuario vehiculoUsuario;

	@OneToMany (mappedBy = "vehiculoAnuncio")
	private Collection <Anuncio> anuncios;
	
	//==============================
	public Vehiculo() {
		this.anuncios = new ArrayList<Anuncio>();
	}
	

	//======================================

	public Vehiculo(String color, String matricula, TipoVehiculo tipoVehiculo,
			Usuario vehiculoUsuario) {
		super();
		this.color = color;
		this.tipoVehiculo = tipoVehiculo;
		this.matricula = matricula;
		this.vehiculoUsuario = vehiculoUsuario;
		this.anuncios = new ArrayList<Anuncio>();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Collection<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(Collection<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Usuario getVehiculoUsuario() {
		return vehiculoUsuario;
	}

	public void setVehiculoUsuario(Usuario vehiculoUsuario) {
		this.vehiculoUsuario = vehiculoUsuario;
	}
	

	
	
	//==============================
	
	
	
}
