package org.proyectoFinal.TFG.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.proyectoFinal.TFG.entities.Anuncio;
import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.entities.Vehiculo;
import org.proyectoFinal.TFG.exception.InfoException;
import org.proyectoFinal.TFG.exception.PRG;
import org.proyectoFinal.TFG.repository.AnuncioRepository;
import org.proyectoFinal.TFG.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

	@Autowired
	private AnuncioRepository anuncioRepository;
	
	public void save(int plaza, LocalDate fecha, LocalTime hora, int precio, LocalTime hora_llegada, Usuario usuario, Vehiculo vehiculo,
			String origen, String destino) {
		
		
		Anuncio anuncio = new Anuncio(plaza,fecha,hora,precio,hora_llegada,usuario,vehiculo,origen,destino);
		
		anuncioRepository.save(anuncio);
		
		
	}

	public int getPrecioRecomendado(String distancia) {
		
		String[] partes = distancia.split(" ");
		
		int numero = Integer.parseInt(partes[0]);
		int precio = 0;
		
		if(numero <= 100) {
			precio = 10;
		}
		if(numero <= 200) {
			precio = 20;
		}
		if(numero <= 300 || numero <= 400) {
			precio = 25;
		}
		if(numero <= 500) {
			precio = 30;
		}
		if(numero > 501) {
			precio = 35;
		}
		
		return precio;
	}

	public LocalTime getHoraLlegada(LocalTime hora, String tiempo) {
		
		//hora de salida 20:58
		//hora
		
		//tiempo que tarda 14 min - 4h 11 min - 1 dia y 11 horas
		String[] partes = tiempo.split(" ");
		int dia,horas,min,num;
		String h,n = "";
		LocalTime hora_llegada = null;
		//14 min
		if(partes.length == 2) {
			 min = Integer.parseInt(partes[0]);
			  hora_llegada = hora.plusMinutes(min);
		}
		//4h 11 min 
		if(partes.length == 3) {
			 h = partes[0];
			 min = Integer.parseInt(partes[1]);
			 
			 char[] cadena = h.toCharArray();
			 for(int i=0; i<cadena.length; i++) {
				 if(Character.isDigit(cadena[i])) {
					 n += cadena[i];
				 }
			 }
			 num =Integer.parseInt(n);
			  hora_llegada = hora.plusHours(num).plusMinutes(min);
		}
		//1 dia y 11 horas
		if(partes.length == 5) {
			
			 dia = Integer.parseInt(partes[0]);
			 horas = Integer.parseInt(partes[3]);
			 Period periodo = Period.ofDays(dia);
			 
			  hora_llegada = hora.plus(periodo).plusHours(horas);
		}
		
		
		
		return hora_llegada;
	}


	public List<Anuncio> findByOrigenAndByDestinoAndByFechaOrderByFechaAsc(String origen, String destino,
			LocalDate fecha) {
		List<Anuncio> anuncios = new ArrayList<>();
		anuncios = anuncioRepository.findByOrigenAndByDestinoAndByFechaOrderByFechaAsc(origen,destino,fecha);
		return anuncios;
	}	
	public List<Anuncio> findByOrigenAndByDestinoOrderByFechaAsc(String origen, String destino) {
		
		List<Anuncio> anuncios = new ArrayList<>();
		anuncios = anuncioRepository.findByOrigenAndByDestinoOrderByFechaAsc(origen,destino);
		return anuncios;
	}
	
	public List<Anuncio> findByOrigenAndByDestinoAndByFechaOrderByFechaAscContaining(String origen, String destino) {
		List<Anuncio> anuncios = new ArrayList<>();
		anuncios = anuncioRepository.findByOrigenAndByDestinoOrderByFechaAscContaining(origen,destino);
		return anuncios;
	}
//	**************************EJEMPLO ALBERTO********************
//	public Collection<Partido> findByJornada(int nJornada) {
//	return partidoRepository.findBynJornadaOrderByFechaAsc(nJornada);
//}
	
	public List<Anuncio> findAll(){
		return anuncioRepository.findAll();
	}

	public Anuncio getById(Long idAnuncio) {
		return anuncioRepository.getById(idAnuncio);
	}
	
	public String getDay(Long idAnuncio) {
		Anuncio anuncio = this.getById(idAnuncio);
		LocalDate fecha = anuncio.getFecha();
		DayOfWeek day = fecha.getDayOfWeek();
		
		
		int dia= day.getValue();
		String nuevoDia = null;
		if(dia == 1) {
			nuevoDia = "Lunes";
		}else if(dia == 2 ) {
			nuevoDia = "Martes";
		}else if(dia == 3){
			nuevoDia = "Miércoles";
		}else if(dia == 4) {
			nuevoDia = "Jueves";
		}else if(dia == 5) {
			nuevoDia = "Viernes";
		}else if(dia == 6) {
			nuevoDia = "Sábado";
		}else if(dia == 7) {
			nuevoDia = "Domingo";
		}
		return nuevoDia;
	}
	
	public String getMes(Long idAnuncio) {
	
		Anuncio anuncio = this.getById(idAnuncio);
		LocalDate fecha = anuncio.getFecha();		
		Month currentMonth = fecha.getMonth();
		
		
		int mes = currentMonth.getValue();
		String nuevoMes = "NO";
		if(mes == 1) {
			nuevoMes = "Enero";
		}else if(mes == 2) {
			nuevoMes = "Febrero";
		}else if(mes == 3) {
			nuevoMes = "Marzo";
		}else if(mes == 4) {
			nuevoMes = "Abril";
		}else if(mes == 5) {
			nuevoMes = "Mayo";
		}else if(mes == 6) {
			nuevoMes = "Junio";
		}else if(mes == 7) {
			nuevoMes = "Julio"; 
		}else if(mes == 8) {
			nuevoMes = "Agosto";
		}else if(mes == 9) {
			nuevoMes = "Septiembre";
		}else if(mes == 10) {
			nuevoMes = "Octubre";
		}else if(mes == 11) {
			nuevoMes = "Noviembre";
		}else if(mes == 12) {
			nuevoMes = "Diciembre";
		}
		return nuevoMes;
	}


	public List<Anuncio> getByConductorId(Usuario usuario) {
		Long id = usuario.getId();
		return anuncioRepository.findByConductorId(id);
		
		
	}

	
	
	public void agregarPasajero(Long idAnuncio, Long idPasajero) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(Long idAnuncio) throws InfoException {
		anuncioRepository.deleteById(idAnuncio);	
		//PRG.info("Exito al borrar el anuncio","/usuario/viaje");
	}




}
