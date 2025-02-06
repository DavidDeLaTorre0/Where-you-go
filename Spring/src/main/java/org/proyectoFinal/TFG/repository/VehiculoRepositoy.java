package org.proyectoFinal.TFG.repository;

import java.util.Collection;

import org.proyectoFinal.TFG.entities.Usuario;
import org.proyectoFinal.TFG.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepositoy extends JpaRepository<Vehiculo, Long>{


	Vehiculo getByVehiculoUsuario(Usuario usuario);


	Collection<Vehiculo> findByVehiculoUsuarioId(Long id);


}
