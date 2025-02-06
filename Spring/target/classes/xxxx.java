import java.time.LocalDate;

import org.agaray.exQuiniela.exception.DangerException;
import org.agaray.exQuiniela.exception.PRG;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@GetMapping("c")
	public String c(ModelMap m) {
		m.put("equipos", equipoService.findAll());
		m.put("view", "partido/cP");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(@RequestParam(value = "nJornada", required = false) int nJornada,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "fecha", required = false) LocalDate fecha,
			@RequestParam(value = "gl", required = false) int gl, @RequestParam(value = "gv", required = false) int gv,
			@RequestParam(value = "idLocal", required = false) Long idLocal,
			@RequestParam(value = "idVisitante", required = false) Long idVisitante) throws DangerException {
		try {
			if (fecha == null || idLocal == null || idVisitante == null) {
				throw new Exception("Los datos fecha, idLocal o idVisitante no pueden ser nulos");
			}
			if (gl < 0 || gv < 0) {
				throw new Exception("Los goles no pueden ser negativos");
			}
			if (nJornada < 1 || nJornada > 50) {
				throw new Exception("El número de jornada debe estar en el rango 1..50");
			}
			if (idLocal == idVisitante) {
				throw new Exception("No se permiten partidos contra uno mismo");
			}

			partidoService.save(nJornada, fecha, gl, gv, idLocal, idVisitante);

		} catch (Exception e) {
			PRG.error(e.getMessage(), "/partido/c");
		}
		return "redirect:/partido/r";
	}
	
	//c de partido
	<form action="/partido/c" method="post">
	<label for="idFecha">Fecha</label>
	<input id="idFecha" type="date" min="1" max="50" value="1" name="fecha"/>
	<br/>
	
	//>Filtrar por fecha</
	<form action="/partido/r">
	<label for="idFecha">Filtrar por fecha</label>
	<input id="idFecha" type="date" name="fecha" onchange="submit()" th:value="${fecha}"/>
</form>
	