package com.libcode.crud.repository;

import com.libcode.crud.model.Asistencia;
import com.libcode.crud.ReporteAsistencia; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    @Query("SELECT a.grupo.nombre as grupo, " +
           "SUM(CASE WHEN a.tardanzas = 0 AND a.faltas = 0 AND a.justificadas = 0 THEN 1 ELSE 0 END) as sinObservaciones, " +
           "SUM(CASE WHEN a.tardanzas > 0 OR a.faltas > 0 OR a.justificadas > 0 THEN 1 ELSE 0 END) as conObservaciones " +
           "FROM Asistencia a GROUP BY a.grupo.nombre")
    List<ReporteAsistencia> generarReporte();
}
