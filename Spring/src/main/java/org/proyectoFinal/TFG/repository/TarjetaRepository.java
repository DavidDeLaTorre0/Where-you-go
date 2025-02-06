package org.proyectoFinal.TFG.repository;

import org.proyectoFinal.TFG.entities.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long>{

	Tarjeta getByUsuarioId(Long id);

}
