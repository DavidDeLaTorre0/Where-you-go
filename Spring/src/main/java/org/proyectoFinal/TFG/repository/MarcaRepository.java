package org.proyectoFinal.TFG.repository;

import org.proyectoFinal.TFG.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MarcaRepository  extends JpaRepository<Marca, Long>{

}
