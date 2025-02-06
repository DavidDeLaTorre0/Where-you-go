package org.proyectoFinal.TFG.repository;

import java.util.List;

import org.proyectoFinal.TFG.entities.Marca;
import org.proyectoFinal.TFG.entities.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long>{

	List<Modelo> getByMarca(Marca marca);


}
