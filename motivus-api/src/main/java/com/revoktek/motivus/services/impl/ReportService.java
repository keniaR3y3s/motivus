package com.revoktek.motivus.services.impl;


import com.revoktek.motivus.services.DynamicQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportService {

    private final DynamicQueryService queryService;
    private final NominatimService nominatimService;

    private String buildDateRangeFilter(Map<String, Object> params, String fieldName) {
        if(params == null) {
            return "";
        }
        boolean hasStart = params.get("fechaInicio") != null;
        boolean hasEnd = params.get("fechaFin") != null;

        if (hasStart && hasEnd) {
            return " AND " + fieldName + " BETWEEN :fechaInicio AND :fechaFin ";
        } else if (hasStart) {
            return " AND " + fieldName + " >= :fechaInicio ";
        } else if (hasEnd) {
            return " AND " + fieldName + " <= :fechaFin ";
        } else {
            return "";
        }
    }

    public Object usuariosDispositivosPorDia(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" usuario.usuario, e.dispositivo, e.version_android_dispositivo AS versionAndroidDispositivo, ");
        sql.append(" DATE(e.fecha_hora_dia) AS fecha, tipo_evento.clave AS tipoEvento  ");
        sql.append(" FROM evento_biometrico e ");
        sql.append(" INNER JOIN usuario ON e.usuario_id = usuario.id_usuario ");
        sql.append(" INNER JOIN tipo_evento ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        if (params.get("fecha") != null) {
            sql.append(" WHERE DATE(e.fecha_hora_dia) = :fecha ");
        }
        sql.append(buildDateRangeFilter(params, "fecha_inicio"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosFallidos(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" usuario.usuario, e.dispositivo, ");
        sql.append(" e.version_android_dispositivo AS versionAndroidDispositivo, ");
        sql.append(" tipo_evento.clave AS tipoEvento , resultado_evento.clave AS resultadoEvento  ");
        sql.append(" FROM evento_biometrico e ");
        sql.append(" LEFT JOIN usuario ON e.usuario_id = usuario.id_usuario ");
        sql.append(" LEFT JOIN tipo_evento ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        sql.append(" LEFT JOIN resultado_evento ON e.resultado_evento_id = resultado_evento.id_resultado_evento ");
        sql.append(" WHERE 1=1 ");
        if (params.get("resultado") != null) {
            sql.append(" AND resultado_evento.clave = :resultado  ");
        }
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object usuariosOffline(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT e.usuario_id) AS total FROM evento_biometrico e   ");
        sql.append(" WHERE 1=1 ");
        if (params.get("offline") != null) {
            sql.append(" AND e.offline_evento = :offline  ");
        }
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosPorTipo(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" tipo_evento.clave AS tipoEvento , COUNT(e.tipo_evento_id) AS total_eventos  ");
        sql.append(" FROM tipo_evento  ");
        sql.append(" LEFT JOIN evento_biometrico e ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        sql.append(" WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY tipo_evento.clave ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object porcentajePromedioPorTipo(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" tipo_evento.clave AS tipoEvento , ROUND(IFNULL(AVG(e.porcentaje), 0), 2) AS porcentajePromedio   ");
        sql.append(" FROM tipo_evento  ");
        sql.append(" LEFT JOIN evento_biometrico e ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        sql.append(" WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY tipo_evento.clave ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosPorEstado(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" resultado_evento.clave AS resultadoEvento , COUNT(e.resultado_evento_id) AS total   ");
        sql.append(" FROM resultado_evento  ");
        sql.append(" LEFT JOIN evento_biometrico e ON e.resultado_evento_id = resultado_evento.id_resultado_evento ");
        sql.append(" WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY resultado_evento.clave ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object validadosPorTflite(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(e.validado_por_tflite) AS total FROM evento_biometrico e  ");
        sql.append(" WHERE 1=1 ");
        if (params.get("validado") != null) {
            sql.append(" AND e.validado_por_tflite = :validado ");
        }
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosConTiempoRespuesta(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("  e.id_transacional AS idTransacional, e.tiempo_respuesta_ms AS tiempoRespuestaMs, ");
        sql.append("  tipo_evento.clave AS tipoEvento, usuario.usuario ");
        sql.append(" FROM evento_biometrico e ");
        sql.append(" LEFT JOIN usuario ON e.usuario_id = usuario.id_usuario ");
        sql.append(" LEFT JOIN tipo_evento ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        sql.append(" WHERE 1=1 ");
        if (params.get("tiempoMs") != null) {
            sql.append(" AND e.tiempo_respuesta_ms > :tiempoMs ");
        }
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        return queryService.executeDynamicQuery(sql.toString(), params);


    }

    public Object dispositivosMasUsados(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT e.dispositivo, COUNT(e.id_evento_biometrico) AS total FROM evento_biometrico e WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY e.dispositivo ORDER BY total DESC ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object ovalAlineado(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT e.oval_alineado AS ovalAlineado, COUNT(e.id_evento_biometrico) AS total FROM evento_biometrico e WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY e.oval_alineado ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object duracionPromedioFuncionalidad(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("  t.funcionalidad, ");
        sql.append("  SEC_TO_TIME( AVG(TIMESTAMPDIFF(SECOND, t.fecha_entrada, t.fecha_salida))) AS duracion ");
        sql.append(" FROM tiempo_funcionalidad t ");
        sql.append(" WHERE  t.fecha_entrada IS NOT NULL AND t.fecha_salida IS NOT NULL ");
        sql.append(buildDateRangeFilter(params, "t.fecha_entrada"));
        sql.append(" GROUP BY t.funcionalidad ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object funcionalidadesMasUsadas(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("  t.funcionalidad, ");
        sql.append("  SEC_TO_TIME( SUM(TIMESTAMPDIFF(SECOND, t.fecha_entrada, t.fecha_salida))) AS duracion ");
        sql.append(" FROM tiempo_funcionalidad t ");
        sql.append(" WHERE  t.fecha_entrada IS NOT NULL AND t.fecha_salida IS NOT NULL ");
        sql.append(buildDateRangeFilter(params, "t.fecha_entrada"));
        sql.append(" GROUP BY t.funcionalidad ");
        sql.append(" ORDER BY duracion DESC ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object mayorTiempoUsoFuncionalidad(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("  DATE(t.fecha_entrada) AS fecha, ");
        sql.append("  SEC_TO_TIME( SUM(TIMESTAMPDIFF(SECOND, t.fecha_entrada, t.fecha_salida))) AS total ");
        sql.append(" FROM tiempo_funcionalidad t ");
        sql.append(" WHERE  t.fecha_entrada IS NOT NULL AND t.fecha_salida IS NOT NULL ");
        if (params.get("funcionalidad") != null) {
            sql.append(" AND t.funcionalidad = :funcionalidad ");
        }
        sql.append(buildDateRangeFilter(params, "t.fecha_entrada"));
        sql.append(" GROUP BY DATE(t.fecha_entrada) ");
        sql.append(" ORDER BY total DESC ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object funcionalidadesMultiplesUsos(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" usuario.usuario, DATE(t.fecha_entrada) AS fecha,  t.funcionalidad,  COUNT(t.id_tiempo_funcionalidad) AS cantidad ");
        sql.append(" FROM tiempo_funcionalidad t ");
        sql.append(" LEFT JOIN usuario ON t.usuario_id = usuario.id_usuario ");
        sql.append(" WHERE 1=1 ");
        sql.append(" GROUP BY usuario.usuario, DATE(t.fecha_entrada), t.funcionalidad  ");
//        sql.append(" HAVING COUNT(t.id_tiempo_funcionalidad) > 1 ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosAbiertos(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" usuario.usuario, t.funcionalidad, t.fecha_entrada AS fechaEntrada ");
        sql.append(" FROM tiempo_funcionalidad t ");
        sql.append(" LEFT JOIN usuario ON t.usuario_id = usuario.id_usuario ");
        sql.append(" WHERE t.fecha_salida IS NULL ");
        sql.append(buildDateRangeFilter(params, "t.fecha_entrada"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosPorDia(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(
                "SELECT DATE(e.fecha_hora_dia) AS fecha, COUNT(*) AS total FROM evento_biometrico e WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY DATE(e.fecha_hora_dia) ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosPorSemana(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(
                "SELECT YEARWEEK(e.fecha_hora_dia, 3) AS semana_iso, COUNT(*) AS total FROM evento_biometrico e WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY YEARWEEK(e.fecha_hora_dia, 3) ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object eventosPorMes(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(
                "SELECT DATE_FORMAT(e.fecha_hora_dia, '%Y-%m') AS mes, COUNT(*) AS total FROM evento_biometrico e WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY DATE_FORMAT(e.fecha_hora_dia, '%Y-%m') ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object porcentajeOffline(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT  ");
        sql.append(" IFNULL(ROUND((SUM(CASE WHEN r.clave = :clave THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2),0) AS porcentaje");
        sql.append(" FROM evento_biometrico e INNER JOIN resultado_evento r ON r.id_resultado_evento = e.resultado_evento_id  ");
        sql.append(" WHERE 1=1 ");
        if (params.get("offline") != null) {
            sql.append(" AND e.offline_evento = :offline  ");
        }
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object tiempoRespuestaPromedio(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT  ");
        sql.append(" tipo_evento.clave AS tipoEvento, IFNULL(ROUND(AVG(e.tiempo_respuesta_ms),2),0) AS tiempoPromedioMs");
        sql.append(" FROM evento_biometrico e ");
        sql.append(" INNER JOIN tipo_evento ON e.tipo_evento_id = tipo_evento.id_tipo_evento ");
        sql.append(" WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "e.fecha_hora_dia"));
        sql.append(" GROUP BY tipo_evento.clave ");
        return queryService.executeDynamicQuery(sql.toString(), params);
    }

    public Object listarFuncionalidadesEstado(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" te.id_tipo_evento, ");
        sql.append(" te.clave, ");
        sql.append(" te.detalle, ");
        sql.append(" te.metodo, ");
        sql.append(" COUNT(CASE WHEN eb.resultado_evento_id = 1 THEN 1 END) AS total_exito, ");
        sql.append(" COUNT(CASE WHEN eb.resultado_evento_id = 2 THEN 1 END) AS total_fallido, ");
        sql.append(" COUNT(CASE WHEN eb.resultado_evento_id = 3 THEN 1 END) AS total_cancelado, ");
        sql.append(" COUNT(CASE WHEN eb.resultado_evento_id = 4 THEN 1 END) AS total_error, ");
        sql.append(" GROUP_CONCAT(DISTINCT CASE WHEN TRIM(eb.entidad_federativa) IS NOT NULL AND TRIM(eb.entidad_federativa) <> '' THEN TRIM(eb.entidad_federativa) END SEPARATOR '|') AS estados ");
        sql.append(" FROM tipo_evento te ");
        sql.append(" LEFT JOIN evento_biometrico eb ON te.id_tipo_evento = eb.tipo_evento_id ");
        sql.append(" WHERE 1=1 ");
        sql.append(buildDateRangeFilter(params, "eb.fecha_hora_dia"));
        sql.append(" GROUP BY te.id_tipo_evento ");
        sql.append(" ORDER BY te.id_tipo_evento ASC ");
        List<Map<String, Object>> result = queryService.executeDynamicQuery(sql.toString(), params);

        try {
            for (Map<String, Object> row : result) {
                String estados = (String) row.get("estados");
                if (estados != null && !estados.trim().isEmpty()) {
                    List<String> federalEntities = Arrays.asList(estados.split("\\|"));
                    row.put("estados", federalEntities);
                } else {
                    row.put("estados", Collections.emptyList());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}

