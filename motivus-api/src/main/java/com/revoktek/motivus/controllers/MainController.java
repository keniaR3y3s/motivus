package com.revoktek.motivus.controllers;

import com.revoktek.motivus.services.impl.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final ReportService reportService;

    @PostMapping("/usuarios-dispositivos-dia")
    public ResponseEntity<?> usuariosDispositivosPorDia(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.usuariosDispositivosPorDia(params));
    }

    @PostMapping("/eventos-fallidos")
    public ResponseEntity<?> eventosFallidos(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosFallidos(params));
    }

    @PostMapping("/usuarios-offline")
    public ResponseEntity<?> usuariosOffline(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.usuariosOffline(params));
    }

    @PostMapping("/eventos-por-tipo")
    public ResponseEntity<?> eventosPorTipo(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosPorTipo(params));
    }

    @PostMapping("/porcentaje-promedio-tipo")
    public ResponseEntity<?> porcentajePromedioPorTipo(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.porcentajePromedioPorTipo(params));
    }

    @PostMapping("/eventos-por-estado")
    public ResponseEntity<?> eventosPorEstado(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosPorEstado(params));
    }

    @PostMapping("/validados-tflite")
    public ResponseEntity<?> validadosPorTflite(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.validadosPorTflite(params));
    }

    @PostMapping("/eventos-tiempo-respuesta")
    public ResponseEntity<?> eventosConTiempoRespuesta(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosConTiempoRespuesta(params));
    }

    @PostMapping("/dispositivos-mas-usados")
    public ResponseEntity<?> dispositivosMasUsados(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.dispositivosMasUsados(params));
    }

    @PostMapping("/oval-alineado")
    public ResponseEntity<?> ovalIncompleto(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.ovalAlineado(params));
    }

    @PostMapping("/duracion-promedio-funcionalidad")
    public ResponseEntity<?> duracionPromedioFuncionalidad(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.duracionPromedioFuncionalidad(params));
    }

    @PostMapping("/funcionalidades-mas-usadas")
    public ResponseEntity<?> funcionalidadesMasUsadas(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.funcionalidadesMasUsadas(params));
    }

    @PostMapping("/mayor-tiempo-uso-funcionalidad")
    public ResponseEntity<?> mayorTiempoUsoFuncionalidad(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.mayorTiempoUsoFuncionalidad(params));
    }

    @PostMapping("/funcionalidades-multiples-usos")
    public ResponseEntity<?> funcionalidadesMultiplesUsos(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.funcionalidadesMultiplesUsos(params));
    }

    @PostMapping("/eventos-abiertos")
    public ResponseEntity<?> eventosAbiertos(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosAbiertos(params));
    }

    @PostMapping("/eventos-por-dia")
    public ResponseEntity<?> eventosPorDia(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosPorDia(params));
    }

    @PostMapping("/eventos-por-semana")
    public ResponseEntity<?> eventosPorSemana(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosPorSemana(params));
    }

    @PostMapping("/eventos-por-mes")
    public ResponseEntity<?> eventosPorMes(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.eventosPorMes(params));
    }

    @PostMapping("/porcentaje-offline")
    public ResponseEntity<?> porcentajeOffline(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.porcentajeOffline(params));
    }

    @PostMapping("/tiempo-respuesta-promedio")
    public ResponseEntity<?> tiempoRespuestaPromedio(@RequestBody Map<String, Object> params) {
        return ResponseEntity.ok(reportService.tiempoRespuestaPromedio(params));
    }

    @PostMapping("/funcionalidades-estado")
    public ResponseEntity<?> listarFuncionalidadesEstado(@RequestBody(required = false) Map<String, Object> params) {
        return ResponseEntity.ok(reportService.listarFuncionalidadesEstado(params));
    }

}
