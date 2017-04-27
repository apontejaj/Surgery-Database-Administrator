-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.16-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for surgery
CREATE DATABASE IF NOT EXISTS `surgery` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `surgery`;

-- Dumping structure for table surgery.appointments
CREATE TABLE IF NOT EXISTS `appointments` (
  `ID_App` int(11) NOT NULL AUTO_INCREMENT,
  `Patient` int(11) NOT NULL,
  `Doctor` int(11) NOT NULL,
  `Symptoms` char(150) DEFAULT NULL,
  `Date` varchar(50) NOT NULL,
  `Time` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_App`),
  KEY `FK__patients` (`Patient`),
  KEY `FK_appointments_users` (`Doctor`),
  CONSTRAINT `FK__patients` FOREIGN KEY (`Patient`) REFERENCES `patients` (`id_pat`),
  CONSTRAINT `FK_appointments_users` FOREIGN KEY (`Doctor`) REFERENCES `users` (`ID_User`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.appointments: ~9 rows (approximately)
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` (`ID_App`, `Patient`, `Doctor`, `Symptoms`, `Date`, `Time`) VALUES
	(19, 1, 1, 'headache', 'tomorrow', 'feo'),
	(20, 1, 1, 'headache', 'hoy', 'feo'),
	(21, 6, 4, NULL, 'thurday', '13:30'),
	(22, 2, 1, NULL, 'hoy', 'ahora'),
	(25, 2, 1, NULL, '06/12/16', '14:00'),
	(27, 6, 4, 'diarrea', '9dec', '15:00'),
	(30, 1, 1, NULL, 'thurday', 'late'),
	(31, 12, 4, 'Cronic Headache for the last three days', '07/01/17', '14:00'),
	(32, 6, 4, NULL, '07/01/17', '17:00');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;

-- Dumping structure for table surgery.bills
CREATE TABLE IF NOT EXISTS `bills` (
  `ID_Bill` int(11) NOT NULL AUTO_INCREMENT,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Patient` int(11) NOT NULL,
  `Status` char(7) NOT NULL DEFAULT 'Unpaid',
  PRIMARY KEY (`ID_Bill`),
  KEY `FK_bills_patients` (`Patient`),
  CONSTRAINT `FK_bills_patients` FOREIGN KEY (`Patient`) REFERENCES `patients` (`id_pat`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.bills: ~6 rows (approximately)
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` (`ID_Bill`, `Date`, `Patient`, `Status`) VALUES
	(5, '2016-12-05 22:07:46', 6, 'Unpaid'),
	(6, '2016-12-05 22:08:59', 1, 'Unpaid'),
	(7, '2016-12-05 22:21:52', 6, 'Paid'),
	(9, '2016-12-30 18:01:13', 12, 'Unpaid'),
	(11, '2016-12-30 18:17:05', 6, 'Unpaid'),
	(12, '2016-12-30 18:28:11', 13, 'Paid');
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;

-- Dumping structure for table surgery.details
CREATE TABLE IF NOT EXISTS `details` (
  `ID_det` int(11) NOT NULL AUTO_INCREMENT,
  `Bill` int(11) NOT NULL DEFAULT '0',
  `Concept` char(50) NOT NULL,
  `Price` int(11) NOT NULL,
  PRIMARY KEY (`ID_det`),
  KEY `FK__bills` (`Bill`),
  CONSTRAINT `FK__bills` FOREIGN KEY (`Bill`) REFERENCES `bills` (`ID_Bill`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.details: ~9 rows (approximately)
/*!40000 ALTER TABLE `details` DISABLE KEYS */;
INSERT INTO `details` (`ID_det`, `Bill`, `Concept`, `Price`) VALUES
	(5, 5, 'Consultation', 50),
	(7, 6, 'Blood Test', 20),
	(8, 6, 'Repeat Prescription', 15),
	(9, 7, 'Blood Test', 20),
	(11, 7, 'Vaccination', 10),
	(12, 5, 'Blood Test', 20),
	(15, 7, 'Repeat Prescription', 15),
	(16, 9, 'Blood Test', 20),
	(17, 9, 'Repeat Prescription', 15),
	(18, 12, 'Consultation', 50),
	(19, 12, 'Blood Test', 20);
/*!40000 ALTER TABLE `details` ENABLE KEYS */;

-- Dumping structure for table surgery.logins
CREATE TABLE IF NOT EXISTS `logins` (
  `login_ID` int(11) NOT NULL AUTO_INCREMENT,
  `username_used` char(50) NOT NULL,
  `Date/Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` char(50) NOT NULL,
  PRIMARY KEY (`login_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.logins: ~18 rows (approximately)
/*!40000 ALTER TABLE `logins` DISABLE KEYS */;
INSERT INTO `logins` (`login_ID`, `username_used`, `Date/Time`, `Status`) VALUES
	(18, 'amilcar', '2016-12-30 15:21:05', 'Lockout'),
	(19, 'kyle', '2016-12-30 15:34:55', 'Success'),
	(20, 'amilcar', '2016-12-30 15:35:52', 'Lockout'),
	(21, 'amilcar', '2016-12-30 16:50:39', 'Success'),
	(22, 'kyle', '2016-12-30 16:51:00', 'Success'),
	(23, 'michael', '2016-12-30 16:52:56', 'Success'),
	(24, 'michael', '2016-12-30 16:55:12', 'Success'),
	(25, 'jhoel', '2016-12-30 16:59:54', 'Success'),
	(26, 'amil', '2016-12-30 17:04:53', 'Failure'),
	(27, 'jhoel', '2016-12-30 17:33:48', 'Success'),
	(28, 'michael', '2016-12-30 17:35:39', 'Success'),
	(29, 'kyle', '2016-12-30 18:05:37', 'Success'),
	(30, 'kyle', '2016-12-30 18:09:53', 'Success'),
	(31, 'kyle', '2016-12-30 18:16:27', 'Success'),
	(32, 'amilcar', '2016-12-30 18:18:33', 'Success'),
	(33, 'michael', '2016-12-30 18:21:13', 'Success'),
	(34, 'jhoel', '2016-12-30 18:24:38', 'Success'),
	(35, 'kyle', '2016-12-30 18:27:52', 'Success'),
	(36, 'michael', '2016-12-30 19:29:59', 'Success');
/*!40000 ALTER TABLE `logins` ENABLE KEYS */;

-- Dumping structure for table surgery.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `ID_Msg` int(11) NOT NULL AUTO_INCREMENT,
  `Date/Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Patient` int(11) NOT NULL,
  `Destination` int(11) NOT NULL,
  `Message` char(250) NOT NULL,
  `Return_Number` char(50) NOT NULL DEFAULT 'Same as patient record',
  `Status` char(50) NOT NULL DEFAULT 'Unread',
  PRIMARY KEY (`ID_Msg`),
  KEY `FK_messages_patients` (`Patient`),
  KEY `FK_messages_users` (`Destination`),
  CONSTRAINT `FK_messages_patients` FOREIGN KEY (`Patient`) REFERENCES `patients` (`id_pat`),
  CONSTRAINT `FK_messages_users` FOREIGN KEY (`Destination`) REFERENCES `users` (`ID_User`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.messages: ~7 rows (approximately)
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`ID_Msg`, `Date/Time`, `Patient`, `Destination`, `Message`, `Return_Number`, `Status`) VALUES
	(14, '2016-12-01 16:20:46', 6, 2, 'hello', 'Same as patient record', 'Unread'),
	(30, '2016-12-03 18:27:29', 2, 1, 'Hellos again this is 30', 'Same as patient record', 'Unread'),
	(35, '2016-12-08 16:54:17', 6, 4, 'be sooner', '', 'Unread'),
	(36, '2016-12-08 17:03:55', 6, 4, 'will be late', 'Same as patient record', 'Unread'),
	(37, '2016-12-30 00:41:19', 1, 1, 'be late', '', 'Read'),
	(38, '2016-12-30 00:41:34', 6, 1, 'be late', '', 'Read'),
	(39, '2016-12-30 16:59:36', 11, 4, 'He is feeling bad', '', 'Read');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;

-- Dumping structure for table surgery.patients
CREATE TABLE IF NOT EXISTS `patients` (
  `ID_Pat` int(11) NOT NULL AUTO_INCREMENT,
  `Name` char(50) NOT NULL,
  `Surname` char(50) NOT NULL,
  `Address` char(50) NOT NULL,
  `Tel_Number` char(50) NOT NULL,
  PRIMARY KEY (`ID_Pat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.patients: ~7 rows (approximately)
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` (`ID_Pat`, `Name`, `Surname`, `Address`, `Tel_Number`) VALUES
	(1, 'Amilcar', 'Jimenez', 'Dublin', '0831793239'),
	(2, 'Kyle', 'Goslin', 'Dublin', '2349876'),
	(3, 'Michael', 'McGloin', '66 Drynam Av.', '0872272483'),
	(6, 'Amilcar', 'Jimenez', 'Caracas', '6721792'),
	(11, 'Daniela', 'Lovera', 'Grand Canal', '12345'),
	(12, 'Mayela', 'Izaguirre', 'Dublin 7', '0987654321'),
	(13, 'Alan', 'Beshoff', 'Howth', '123456');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;

-- Dumping structure for table surgery.prescriptions
CREATE TABLE IF NOT EXISTS `prescriptions` (
  `ID_Presc` int(11) NOT NULL AUTO_INCREMENT,
  `Consultation` int(11) NOT NULL,
  `Medication` char(50) NOT NULL,
  PRIMARY KEY (`ID_Presc`),
  KEY `FK__appointments` (`Consultation`),
  CONSTRAINT `FK__appointments` FOREIGN KEY (`Consultation`) REFERENCES `appointments` (`ID_App`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.prescriptions: ~21 rows (approximately)
/*!40000 ALTER TABLE `prescriptions` DISABLE KEYS */;
INSERT INTO `prescriptions` (`ID_Presc`, `Consultation`, `Medication`) VALUES
	(1, 19, 'VND1'),
	(2, 19, 'XXV2'),
	(4, 21, 'nada'),
	(5, 22, '543H'),
	(7, 20, 'ahi vamos'),
	(8, 20, 'HH5'),
	(9, 20, 'X34'),
	(12, 22, '344BB'),
	(13, 22, 'DDF23'),
	(14, 27, 'looperan'),
	(15, 22, 'JHH7'),
	(16, 20, 'VND1'),
	(17, 19, 'VND1'),
	(18, 22, 'HNF232'),
	(19, 22, 'GB334'),
	(20, 31, 'VND1'),
	(22, 31, 'XXV2'),
	(23, 31, 'X34'),
	(24, 31, 'JHH7'),
	(25, 32, '543H'),
	(26, 32, '543H');
/*!40000 ALTER TABLE `prescriptions` ENABLE KEYS */;

-- Dumping structure for table surgery.users
CREATE TABLE IF NOT EXISTS `users` (
  `ID_User` int(11) NOT NULL AUTO_INCREMENT,
  `Name` char(50) NOT NULL,
  `Surname` char(50) NOT NULL,
  `Type` char(50) NOT NULL,
  `Username` char(50) NOT NULL,
  `Password` char(50) NOT NULL,
  PRIMARY KEY (`ID_User`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table surgery.users: ~3 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`ID_User`, `Name`, `Surname`, `Type`, `Username`, `Password`) VALUES
	(1, 'Amilcar', 'Aponte', 'Doctor', 'amilcar', 'aponte'),
	(2, 'Michael', 'McGloin', 'Receptionist', 'michael', 'mcgloin'),
	(3, 'Kyle', 'Goslin', 'Billing', 'kyle', 'goslin'),
	(4, 'Amilcar', 'Jhoel', 'Doctor', 'jhoel', 'jimenez');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
