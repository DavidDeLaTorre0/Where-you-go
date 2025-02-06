package org.proyectoFinal.TFG.repository;

import java.time.LocalDate;
import java.util.List;

import org.proyectoFinal.TFG.entities.Anuncio;
import org.proyectoFinal.TFG.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long>{

	List<Anuncio> findByConductorId(Long id);

	List<Anuncio> findByOrigenAndByDestinoAndByFechaOrderByFechaAsc(String origen, String destino, LocalDate fecha);	
	List<Anuncio> findByOrigenAndByDestinoOrderByFechaAsc(String origen, String destino);

	//si no hay anuncios hacia esa calle, que saque un viaje a la ciudad/ provincia de ese el pais
	List<Anuncio> findByOrigenAndByDestinoOrderByFechaAscContaining(String nuevoOrigen, String nuevoDestino);

}

/*		DOCS SPRING
Example 13. Query creation from method names
interface PersonRepository extends Repository<Person, Long> {

  List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

  // Enables the distinct flag for the query
  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

  // Enabling ignoring case for an individual property
  List<Person> findByLastnameIgnoreCase(String lastname);
  // Enabling ignoring case for all suitable properties
  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

  // Enabling static ORDER BY for a query
  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
*/