package org.proyectoFinal.TFG.entities;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Anuncio {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int plaza;
	
	private LocalDate fecha;
	private LocalTime hora;
	private int precio;
    private String origen;
    private String destino;
    private LocalTime horaLlegada;
    
	@ManyToOne
	private Usuario conductor;
	
	@ManyToMany
	private Collection<Usuario> pasajeros;

    @ManyToOne
    private Vehiculo vehiculoAnuncio;
    

	
	public Anuncio() {
		this.pasajeros = new ArrayList<Usuario>();
	}


	public Anuncio(int plaza, LocalDate fecha, LocalTime hora, int precio,LocalTime horaLlegada, Usuario conductor,
			Vehiculo vehiculoAnuncio, String origen, String destino) {
		super();
		this.plaza = plaza;
		this.fecha = fecha;
		this.hora = hora;
		this.precio = precio;
		this.horaLlegada = horaLlegada;
		this.conductor = conductor;
		this.vehiculoAnuncio = vehiculoAnuncio;
		this.origen = origen;
		this.destino = destino;
		this.pasajeros = new ArrayList<Usuario>();
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getPlaza() {
		return plaza;
	}


	public void setPlaza(int plaza) {
		this.plaza = plaza;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public LocalTime getHora() {
		return hora;
	}


	public void setHora(LocalTime hora) {
		this.hora = hora;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public void setHoraLlegada(LocalTime horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	
	public LocalTime getHoraLlegada() {
		return horaLlegada;
	}

	public Usuario getConductor() {
		return conductor;
	}


	public void setConductor(Usuario conductor) {
		this.conductor = conductor;
	}


	public Collection<Usuario> getPasajeros() {
		return pasajeros;
	}


	public void setPasajeros(Collection<Usuario> pasajeros) {
		this.pasajeros = pasajeros;
	}


	public Vehiculo getVehiculoAnuncio() {
		return vehiculoAnuncio;
	}


	public void setVehiculoAnuncio(Vehiculo vehiculoAnuncio) {
		this.vehiculoAnuncio = vehiculoAnuncio;
	}


	public String getOrigen() {
		return origen;
	}


	public void setOrigen(String origen) {
		this.origen = origen;
	}


	public String getDestino() {
		return destino;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}

	
	//==================================================
	
	
	//RELACION MANY TO MANY, ASI GUARDAMOS LOS PASAJEROS
	
	public void addPasajeros(Usuario usuario, int plaza) {
		
		if(plaza > 0) {
		this.pasajeros.add(usuario);
		usuario.getUsuarioPasajero().add(this);
		this.plaza= plaza-1;
		}//SINO QUE ENVIE UN MENSG DE ERROR
	}
	
//	public String getRol() {
//		return nombre.equals("pepe") ? "admin" : "auth";
//	}
	
}