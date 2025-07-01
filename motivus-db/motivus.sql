/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : motivus

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 01/07/2025 11:32:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evento_biometrico
-- ----------------------------
DROP TABLE IF EXISTS `evento_biometrico`;
CREATE TABLE `evento_biometrico`  (
  `id_evento_biometrico` bigint NOT NULL AUTO_INCREMENT,
  `id_transacional` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fecha_hora_dia` datetime NOT NULL,
  `porcentaje` decimal(5, 2) NULL DEFAULT NULL,
  `version_android_dispositivo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dispositivo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gps` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `resultado_descripcion` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tiempo_respuesta_ms` int NULL DEFAULT NULL,
  `tiempo_proceso_ms` int NULL DEFAULT NULL,
  `oval_alineado` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `validado_por_tflite` tinyint(1) NULL DEFAULT NULL,
  `offline_evento` tinyint(1) NULL DEFAULT NULL,
  `sincronizado` tinyint(1) NULL DEFAULT 0,
  `usuario_id` bigint NOT NULL,
  `tipo_evento_id` bigint NOT NULL,
  `resultado_evento_id` bigint NOT NULL,
  PRIMARY KEY (`id_evento_biometrico`) USING BTREE,
  INDEX `usuario_id`(`usuario_id` ASC) USING BTREE,
  INDEX `tipo_evento_id`(`tipo_evento_id` ASC) USING BTREE,
  INDEX `resultado_evento_id`(`resultado_evento_id` ASC) USING BTREE,
  CONSTRAINT `evento_biometrico_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `evento_biometrico_ibfk_2` FOREIGN KEY (`tipo_evento_id`) REFERENCES `tipo_evento` (`id_tipo_evento`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `evento_biometrico_ibfk_3` FOREIGN KEY (`resultado_evento_id`) REFERENCES `resultado_evento` (`id_resultado_evento`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evento_biometrico
-- ----------------------------
INSERT INTO `evento_biometrico` VALUES (1, '01010101', '2025-06-26 09:04:30', 80.00, '1602515', 'Samsung Galaxy', '10,10', '- - -', 30, 60, 'INCOMPLETO', 1, 1, 0, 1, 1, 1);

-- ----------------------------
-- Table structure for resultado_evento
-- ----------------------------
DROP TABLE IF EXISTS `resultado_evento`;
CREATE TABLE `resultado_evento`  (
  `id_resultado_evento` bigint NOT NULL AUTO_INCREMENT,
  `clave` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `descripcion` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_resultado_evento`) USING BTREE,
  UNIQUE INDEX `clave`(`clave` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resultado_evento
-- ----------------------------
INSERT INTO `resultado_evento` VALUES (1, 'EXITO', 'Evento exitoso');
INSERT INTO `resultado_evento` VALUES (2, 'FALLIDO', 'Evento fallido');
INSERT INTO `resultado_evento` VALUES (3, 'CANCELADO', 'Evento cancelado por el usuario');
INSERT INTO `resultado_evento` VALUES (4, 'ERROR', 'Evento con error inesperado');

-- ----------------------------
-- Table structure for tiempo_funcionalidad
-- ----------------------------
DROP TABLE IF EXISTS `tiempo_funcionalidad`;
CREATE TABLE `tiempo_funcionalidad`  (
  `id_tiempo_funcionalidad` bigint NOT NULL AUTO_INCREMENT,
  `funcionalidad` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fecha_entrada` datetime NOT NULL,
  `fecha_salida` datetime NULL DEFAULT NULL,
  `duracion_horas` int GENERATED ALWAYS AS (floor((timestampdiff(SECOND,`fecha_entrada`,`fecha_salida`) / 3600))) STORED NULL,
  `duracion_minutos` int GENERATED ALWAYS AS (floor(((timestampdiff(SECOND,`fecha_entrada`,`fecha_salida`) % 3600) / 60))) STORED NULL,
  `duracion_solo_segundos` int GENERATED ALWAYS AS ((timestampdiff(SECOND,`fecha_entrada`,`fecha_salida`) % 60)) STORED NULL,
  `usuario_id` bigint NOT NULL,
  `evento_biometrico_id` bigint NOT NULL,
  PRIMARY KEY (`id_tiempo_funcionalidad`) USING BTREE,
  INDEX `evento_biometrico_id`(`evento_biometrico_id` ASC) USING BTREE,
  INDEX `usuario_id`(`usuario_id` ASC) USING BTREE,
  CONSTRAINT `tiempo_funcionalidad_ibfk_1` FOREIGN KEY (`evento_biometrico_id`) REFERENCES `evento_biometrico` (`id_evento_biometrico`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tiempo_funcionalidad_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tiempo_funcionalidad
-- ----------------------------
INSERT INTO `tiempo_funcionalidad` VALUES (1, 'CAPTURA_FACIAL', '2025-06-26 09:04:30', '2025-06-26 09:05:20', DEFAULT, DEFAULT, DEFAULT, 1, 1);

-- ----------------------------
-- Table structure for tipo_evento
-- ----------------------------
DROP TABLE IF EXISTS `tipo_evento`;
CREATE TABLE `tipo_evento`  (
  `id_tipo_evento` bigint NOT NULL AUTO_INCREMENT,
  `clave` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `detalle` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `metodo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_tipo_evento`) USING BTREE,
  UNIQUE INDEX `clave`(`clave` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tipo_evento
-- ----------------------------
INSERT INTO `tipo_evento` VALUES (1, 'CAPTURA_FACIAL', 'Captura de imagen facial', 'FACIAL');
INSERT INTO `tipo_evento` VALUES (2, 'VALIDACION_TFLITE', 'Inferencia con modelo .tflite', 'FACIAL');
INSERT INTO `tipo_evento` VALUES (3, 'VIDEO_PRUEBA_VIDA', 'Validación de vida con movimientos de cabeza', 'FACIAL');
INSERT INTO `tipo_evento` VALUES (4, 'HUELLA', 'Validación por huella digital', 'HUELLA');
INSERT INTO `tipo_evento` VALUES (5, 'QR', 'Validación por código QR', 'LECTURA');

-- ----------------------------
-- Table structure for usuario
-- ----------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario`  (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `usuario` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_usuario`) USING BTREE,
  UNIQUE INDEX `usuario`(`usuario` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of usuario
-- ----------------------------
INSERT INTO `usuario` VALUES (1, 'USUARIO_001');

SET FOREIGN_KEY_CHECKS = 1;
