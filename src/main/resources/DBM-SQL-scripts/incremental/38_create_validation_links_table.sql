CREATE TABLE IF NOT EXISTS `quicktour`.`validation_links` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `validationLink` VARCHAR(256),
  `time_registered` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`Id`))
ENGINE = InnoDB;