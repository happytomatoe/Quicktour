

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `quicktour` DEFAULT CHARACTER SET utf8 ;
USE `quicktour` ;

-- -----------------------------------------------------
-- Table `quicktour`.`Photos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Photos` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PhotoUrl` VARCHAR(80) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Roles` (
  `RoleId` INT NOT NULL,
  `Role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`RoleId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Users` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `Phone` VARCHAR(45) NULL DEFAULT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Name` VARCHAR(45) NULL,
  `Surname` VARCHAR(45) NULL,
  `Age` INT NULL DEFAULT NULL,
  `Sex` VARCHAR(45) NULL DEFAULT NULL,
  `Company_Code` VARCHAR(20) NOT NULL,
  `Photos_ID` INT NULL,
  `Roles_RoleId` INT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `fk_Users_Photos1_idx` (`Photos_ID` ASC),
  INDEX `fk_Users_Roles1_idx` (`Roles_RoleId` ASC),
  CONSTRAINT `fk_Users_Photos1`
    FOREIGN KEY (`Photos_ID`)
    REFERENCES `quicktour`.`Photos` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_Roles1`
    FOREIGN KEY (`Roles_RoleId`)
    REFERENCES `quicktour`.`Roles` (`RoleId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `quicktour`.`Companies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Companies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Information` LONGTEXT NULL,
  `Address` VARCHAR(256) NULL,
  `Contact_Phone` VARCHAR(12) NOT NULL,
  `Contact_Email` VARCHAR(20) NOT NULL,
  `Type` VARCHAR(10) NOT NULL,
  `Discount_amount` INT NULL DEFAULT NULL,
  `License` VARCHAR(20) NOT NULL,
  `Company_Code` VARCHAR(20) NULL DEFAULT NULL,
  `Photos_ID` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Companies_Photos1_idx` (`Photos_ID` ASC),
  CONSTRAINT `fk_Companies_Photos1`
    FOREIGN KEY (`Photos_ID`)
    REFERENCES `quicktour`.`Photos` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Company_Creation_Requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Company_Creation_Requests` (
  `UserID` INT NOT NULL,
  `CompanyID` INT NOT NULL,
  `Status` VARCHAR(45) NOT NULL,
  `Date` DATETIME NOT NULL,
  INDEX `fk_Company_Creation_Requests_Users_idx` (`UserID` ASC),
  INDEX `fk_Company_Creation_Requests_Companies1_idx` (`CompanyID` ASC),
  PRIMARY KEY (`CompanyID`, `UserID`),
  CONSTRAINT `fk_Company_Creation_Requests_Users`
    FOREIGN KEY (`UserID`)
    REFERENCES `quicktour`.`Users` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Company_Creation_Requests_Companies1`
    FOREIGN KEY (`CompanyID`)
    REFERENCES `quicktour`.`Companies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`discount_policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`discount_policy` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `formula` VARCHAR(20) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `startDate` DATE NULL DEFAULT NULL,
  `endDate` DATE NULL DEFAULT NULL,
  `condition` VARCHAR(50) NULL DEFAULT NULL,
  `Companies_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_discount_policy_Companies1_idx` (`Companies_id` ASC),
  CONSTRAINT `fk_discount_policy_Companies1`
    FOREIGN KEY (`Companies_id`)
    REFERENCES `quicktour`.`Companies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`discount_dependency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`discount_dependency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NULL DEFAULT NULL,
  `table_field` VARCHAR(45) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `basedOn` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Tours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Tours` (
  `ToursId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(128) NULL DEFAULT NULL,
  `Description` VARCHAR(4096) NULL DEFAULT NULL,
  `TransportDesc` VARCHAR(512) NULL DEFAULT NULL,
  `MainPhotoUrl` VARCHAR(256) NULL DEFAULT NULL,
  `IsActive` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ToursId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Tour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Tour` (
  `TourId` INT NOT NULL AUTO_INCREMENT,
  `ToursId` INT NOT NULL,
  `StartDate` DATE NULL DEFAULT NULL,
  `Term` TINYINT NULL DEFAULT NULL,
  `Discount` INT(3) NULL DEFAULT NULL,
  PRIMARY KEY (`TourId`),
  INDEX `fk_Tour_Tours_idx` (`ToursId` ASC),
  UNIQUE INDEX `TourId_UNIQUE` (`TourId` ASC),
  CONSTRAINT `fk_Tour_Tours`
    FOREIGN KEY (`ToursId`)
    REFERENCES `quicktour`.`Tours` (`ToursId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Places`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Places` (
  `PlaceId` INT NOT NULL AUTO_INCREMENT,
  `Country` VARCHAR(45) NULL DEFAULT NULL,
  `Name` VARCHAR(128) NULL DEFAULT NULL,
  `Description` VARCHAR(4096) NULL DEFAULT NULL,
  `IsOptional` TINYINT(1) NULL DEFAULT NULL,
  `Price` SMALLINT NULL DEFAULT NULL,
  `GeoHeight` DOUBLE NULL DEFAULT NULL,
  `GeoWidth` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`PlaceId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Excursions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Excursions` (
  `ExcursId` INT NOT NULL AUTO_INCREMENT,
  `PlaceId` INT NOT NULL,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `IsOptional` TINYINT(1) NULL DEFAULT NULL,
  `Price` SMALLINT NULL DEFAULT NULL,
  PRIMARY KEY (`ExcursId`),
  INDEX `fk_Excursions_Places1_idx` (`PlaceId` ASC),
  CONSTRAINT `fk_Excursions_Places1`
    FOREIGN KEY (`PlaceId`)
    REFERENCES `quicktour`.`Places` (`PlaceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Tours_has_Places`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Tours_has_Places` (
  `ToursId` INT NOT NULL,
  `PlaceId` INT NOT NULL,
  PRIMARY KEY (`ToursId`, `PlaceId`),
  INDEX `fk_Tours_has_Places_Places1_idx` (`PlaceId` ASC),
  INDEX `fk_Tours_has_Places_Tours1_idx` (`ToursId` ASC),
  CONSTRAINT `fk_Tours_has_Places_Tours1`
    FOREIGN KEY (`ToursId`)
    REFERENCES `quicktour`.`Tours` (`ToursId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tours_has_Places_Places1`
    FOREIGN KEY (`PlaceId`)
    REFERENCES `quicktour`.`Places` (`PlaceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`PriceIncludes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`PriceIncludes` (
  `PriceIncludesId` INT NOT NULL AUTO_INCREMENT,
  `IncludeDescription` VARCHAR(128) NULL DEFAULT NULL,
  PRIMARY KEY (`PriceIncludesId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Tours_has_PriceIncludes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Tours_has_PriceIncludes` (
  `ToursId` INT NOT NULL,
  `PriceIncludesId` INT NOT NULL,
  PRIMARY KEY (`ToursId`, `PriceIncludesId`),
  INDEX `fk_Tours_has_PriceIncludes_PriceIncludes1_idx` (`PriceIncludesId` ASC),
  INDEX `fk_Tours_has_PriceIncludes_Tours1_idx` (`ToursId` ASC),
  CONSTRAINT `fk_Tours_has_PriceIncludes_Tours1`
    FOREIGN KEY (`ToursId`)
    REFERENCES `quicktour`.`Tours` (`ToursId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tours_has_PriceIncludes_PriceIncludes1`
    FOREIGN KEY (`PriceIncludesId`)
    REFERENCES `quicktour`.`PriceIncludes` (`PriceIncludesId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Orders` (
  `id` INT UNSIGNED NOT NULL,
  `order_date` DATETIME NULL,
  `number_of_adults` INT NULL DEFAULT NULL,
  `number_of_children` INT NULL DEFAULT NULL,
  `user_info` TEXT NULL DEFAULT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `discount` FLOAT NULL DEFAULT NULL,
  `status` VARCHAR(45) NULL DEFAULT NULL,
  `TourId` INT NOT NULL,
  `Companies_id` INT NOT NULL,
  `Users_ID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Orders_Tour1_idx` (`TourId` ASC),
  INDEX `fk_Orders_Companies1_idx` (`Companies_id` ASC),
  INDEX `fk_Orders_Users1_idx` (`Users_ID` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Orders_Tour1`
    FOREIGN KEY (`TourId`)
    REFERENCES `quicktour`.`Tour` (`TourId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_Companies1`
    FOREIGN KEY (`Companies_id`)
    REFERENCES `quicktour`.`Companies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_Users1`
    FOREIGN KEY (`Users_ID`)
    REFERENCES `quicktour`.`Users` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Places_has_Photos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Places_has_Photos` (
  `Places_PlaceId` INT NOT NULL,
  `Photos_ID` INT NOT NULL,
  PRIMARY KEY (`Places_PlaceId`, `Photos_ID`),
  INDEX `fk_Places_has_Photos_Photos1_idx` (`Photos_ID` ASC),
  INDEX `fk_Places_has_Photos_Places1_idx` (`Places_PlaceId` ASC),
  CONSTRAINT `fk_Places_has_Photos_Places1`
    FOREIGN KEY (`Places_PlaceId`)
    REFERENCES `quicktour`.`Places` (`PlaceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Places_has_Photos_Photos1`
    FOREIGN KEY (`Photos_ID`)
    REFERENCES `quicktour`.`Photos` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `quicktour`.`Tours_has_discount_policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `quicktour`.`Tours_has_discount_policy` (
  `Tours_ToursId` INT NOT NULL,
  `discount_policy_id` INT NOT NULL,
  PRIMARY KEY (`Tours_ToursId`, `discount_policy_id`),
  INDEX `fk_Tours_has_discount_policy_discount_policy1_idx` (`discount_policy_id` ASC),
  INDEX `fk_Tours_has_discount_policy_Tours1_idx` (`Tours_ToursId` ASC),
  CONSTRAINT `fk_Tours_has_discount_policy_Tours1`
    FOREIGN KEY (`Tours_ToursId`)
    REFERENCES `quicktour`.`Tours` (`ToursId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tours_has_discount_policy_discount_policy1`
    FOREIGN KEY (`discount_policy_id`)
    REFERENCES `quicktour`.`discount_policy` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
