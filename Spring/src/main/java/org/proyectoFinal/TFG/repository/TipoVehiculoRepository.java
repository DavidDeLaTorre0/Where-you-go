package org.proyectoFinal.TFG.repository;

import java.util.List;

import org.proyectoFinal.TFG.entities.Modelo;
import org.proyectoFinal.TFG.entities.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long>{

	List<TipoVehiculo> getByModelo(Modelo modelo);

}
