UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='1';
UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='2';
UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='3';
UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='4';
UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='5';
UPDATE `quicktour`.`tour` SET `Term`=null WHERE `TourId`='6';

ALTER TABLE `quicktour`.`tour`
CHANGE COLUMN `Term` `EndDate` DATE NULL DEFAULT NULL ;

UPDATE `quicktour`.`tour` SET `EndDate`='2013-11-25' WHERE `TourId`='1';
UPDATE `quicktour`.`tour` SET `EndDate`='2013-11-30' WHERE `TourId`='2';
UPDATE `quicktour`.`tour` SET `EndDate`='2013-12-05' WHERE `TourId`='3';
UPDATE `quicktour`.`tour` SET `EndDate`='2014-01-05' WHERE `TourId`='4';
UPDATE `quicktour`.`tour` SET `EndDate`='2014-01-15' WHERE `TourId`='5';
UPDATE `quicktour`.`tour` SET `EndDate`='2013-11-14' WHERE `TourId`='6';