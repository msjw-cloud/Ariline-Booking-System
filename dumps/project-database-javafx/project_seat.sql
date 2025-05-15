CREATE DATABASE  IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `project`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `seatID` int NOT NULL AUTO_INCREMENT,
  `seatNumber` varchar(45) NOT NULL,
  `seatType` varchar(45) NOT NULL,
  `status` varchar(1) NOT NULL,
  `AType` varchar(45) NOT NULL,
  `MYear` date NOT NULL,
  `flightID` int NOT NULL,
  PRIMARY KEY (`seatID`),
  KEY `AType_idx` (`AType`),
  KEY `MYear_idx` (`MYear`),
  KEY `flightID_idx` (`flightID`),
  CONSTRAINT `AType_fk` FOREIGN KEY (`AType`) REFERENCES `aircraft` (`AType`),
  CONSTRAINT `flightID_fk` FOREIGN KEY (`flightID`) REFERENCES `flight` (`flightID`),
  CONSTRAINT `MYear_fk` FOREIGN KEY (`MYear`) REFERENCES `aircraft` (`MYear`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES (1,'1a','f','n','Airbus','2020-01-10',1),(2,'1b','f','n','Airbus','2020-01-10',1),(3,'1c','f','n','Airbus','2020-01-10',1),(4,'1d','f','y','Airbus','2020-01-10',1),(5,'2a','b','y','Airbus','2020-01-10',1),(6,'2b','b','n','Airbus','2020-01-10',1),(7,'2c','b','y','Airbus','2020-01-10',1),(8,'2d','b','y','Airbus','2020-01-10',1),(9,'3a','e','y','Airbus','2020-01-10',1),(10,'3b','e','n','Airbus','2020-01-10',1),(11,'3c','e','y','Airbus','2020-01-10',1),(12,'3d','e','y','Airbus','2020-01-10',1),(13,'4a','e','y','Airbus','2020-01-10',1),(14,'4b','e','y','Airbus','2020-01-10',1),(15,'4c','e','n','Airbus','2020-01-10',1),(16,'4d','e','n','Airbus','2020-01-10',1),(17,'1a','f','y','Boeing','2021-01-10',20),(18,'1b','f','y','Boeing','2021-01-10',20),(19,'1c','f','y','Boeing','2021-01-10',20),(20,'1d','f','y','Boeing','2021-01-10',20),(21,'2a','f','y','Boeing','2021-01-10',20),(22,'2b','f','y','Boeing','2021-01-10',20),(23,'2c','b','y','Boeing','2021-01-10',20),(24,'2d','b','y','Boeing','2021-01-10',20),(25,'3a','b','y','Boeing','2021-01-10',20),(26,'3b','b','y','Boeing','2021-01-10',20),(27,'3c','b','y','Boeing','2021-01-10',20),(28,'3d','b','y','Boeing','2021-01-10',20),(29,'4a','e','y','Boeing','2021-01-10',20),(30,'4b','e','y','Boeing','2021-01-10',20),(31,'4c','e','y','Boeing','2021-01-10',20),(32,'4d','e','y','Boeing','2021-01-10',20),(33,'5a','e','y','Boeing','2021-01-10',20),(34,'5b','e','y','Boeing','2021-01-10',20),(35,'5c','e','y','Boeing','2021-01-10',20),(36,'5d','e','y','Boeing','2021-01-10',20),(37,'6a','e','y','Boeing','2021-01-10',20),(38,'6b','e','y','Boeing','2021-01-10',20),(39,'6c','e','y','Boeing','2021-01-10',20),(40,'6d','e','y','Boeing','2021-01-10',20),(41,'1a','f','n','Boeing','2021-01-10',21),(42,'1b','f','y','Boeing','2021-01-10',21),(43,'1c','f','y','Boeing','2021-01-10',21),(44,'1d','f','y','Boeing','2021-01-10',21),(45,'1e','f','y','Boeing','2021-01-10',21),(46,'1f','f','y','Boeing','2021-01-10',21),(47,'2a','f','y','Boeing','2021-01-10',21),(48,'2b','f','y','Boeing','2021-01-10',21),(49,'2c','f','y','Boeing','2021-01-10',21),(50,'2d','f','n','Boeing','2021-01-10',21),(51,'2e','f','n','Boeing','2021-01-10',21),(52,'2f','f','n','Boeing','2021-01-10',21),(53,'3a','b','y','Boeing','2021-01-10',21),(54,'3b','b','y','Boeing','2021-01-10',21),(55,'3c','b','y','Boeing','2021-01-10',21),(56,'3d','b','y','Boeing','2021-01-10',21),(57,'3e','b','y','Boeing','2021-01-10',21),(58,'3f','b','y','Boeing','2021-01-10',21),(59,'4a','b','y','Boeing','2021-01-10',21),(60,'4b','b','y','Boeing','2021-01-10',21),(61,'4c','b','y','Boeing','2021-01-10',21),(62,'4d','b','y','Boeing','2021-01-10',21),(63,'4e','b','n','Boeing','2021-01-10',21),(64,'4f','b','n','Boeing','2021-01-10',21),(65,'5a','e','y','Boeing','2021-01-10',21),(66,'5b','e','y','Boeing','2021-01-10',21),(67,'5c','e','y','Boeing','2021-01-10',21),(68,'5d','e','y','Boeing','2021-01-10',21),(69,'5e','e','y','Boeing','2021-01-10',21),(70,'5f','e','y','Boeing','2021-01-10',21),(71,'6a','e','y','Boeing','2021-01-10',21),(72,'6b','e','y','Boeing','2021-01-10',21),(73,'6c','e','y','Boeing','2021-01-10',21),(74,'6d','e','y','Boeing','2021-01-10',21),(75,'6e','e','y','Boeing','2021-01-10',21),(76,'6f','e','y','Boeing','2021-01-10',21),(77,'7a','e','y','Boeing','2021-01-10',21),(78,'7b','e','y','Boeing','2021-01-10',21),(79,'7c','e','y','Boeing','2021-01-10',21),(80,'7d','e','y','Boeing','2021-01-10',21),(81,'7e','e','n','Boeing','2021-01-10',21),(82,'7f','e','n','Boeing','2021-01-10',21);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09 17:10:25
