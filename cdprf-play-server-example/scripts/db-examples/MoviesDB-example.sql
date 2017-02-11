USE `SSP_DB`;

/*******************************************************
Reset Schema
********************************************************/
DROP TABLE IF EXISTS `MOVIES_DB`.`MOVIE`;
DROP TABLE IF EXISTS `MOVIES_DB`.`MOVIE_BOOK`;

/*******************************************************
Create Schema
********************************************************/
CREATE TABLE `MOVIES_DB`.`MOVIE` (
  `ID` int(11) unsigned NOT NULL auto_increment,
  `TITLE` varchar(60) NOT NULL,
  `COUNTRY` varchar(30) NOT NULL,  
  `LANGUAGE` varchar(30) NOT NULL,  
  `GENRE` enum('Drama', 'Comedy', 'Action', 'Animation', 'Documentary', 'Romance', 'Crime') 
     CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
   `RELEASED_ON` datetime NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MOVIES_DB`.`MOVIE_BOOK` (
  `MOVIE_ID` int(11) unsigned NOT NULL,
  `TITLE` varchar(60) NULL,
  `AUTHOR` varchar(60) NULL,
  `COUNTRY` varchar(30) NOT NULL,  
  `LANGUAGE` varchar(30) NOT NULL,  
  CONSTRAINT `PRIMARY` PRIMARY KEY (`MOVIE_ID`),
  CONSTRAINT `FK_MOVIE_ID` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES_DB`.`MOVIE` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

