package org.proyectoFinal.TFG.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Tarjeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titularTarjeta;
	
	@Column(unique = true)
	private String numeroTarjeta;
	
	private String mes;
	private String anio;
	
	private String cvv;
	
	@OneToOne
	private Usuario usuario;
	
	
	public Tarjeta() {

	}


	public Tarjeta(String titularTarjeta, String numeroTarjeta, String mes, String anio, String cvv, Usuario usuario) {
		super();
		this.titularTarjeta = titularTarjeta;
		this.numeroTarjeta = numeroTarjeta;
		this.mes = mes;
		this.anio = anio;
		this.cvv = cvv;
		this.usuario = usuario;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitularTarjeta() {
		return titularTarjeta;
	}


	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}


	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}


	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}


	public String getMes() {
		return mes;
	}


	public void setMes(String mes) {
		this.mes = mes;
	}


	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}


	public String getCvv() {
		return cvv;
	}


	public void setCvv(String cvv) {
		this.cvv = cvv;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	
}
