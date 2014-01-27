RENAME TABLE `quicktour`.`tour` TO `quicktour`.`tourInfo`;
DROP TABLE `Company_Creation_Requests`;
RENAME TABLE `quicktour`.`tours_has_places` TO `quicktour`.`tours_places`;
RENAME TABLE `quicktour`.`tours_has_priceincludes` TO `quicktour`.`tours_price_includes`;


ALTER TABLE `comments` CHANGE `comment_id`  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT;
ALTER TABLE `excursions` CHANGE `ExcursId`  `id` INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `photos` CHANGE `ID`  `id` INT(11) NOT NULL AUTO_INCREMENT;
#  ALTER TABLE  `places` CHANGE  `PlaceId`  `id` INT( 11 ) NOT NULL AUTO_INCREMENT ;
ALTER TABLE `priceincludes` CHANGE `IncludeDescription`  `description` VARCHAR(128)
CHARACTER SET utf8
COLLATE utf8_general_ci NULL DEFAULT NULL;
ALTER TABLE `photos` CHANGE `PhotoUrl`  `url` VARCHAR(256)
CHARACTER SET utf8
COLLATE utf8_general_ci NULL DEFAULT NULL;
ALTER TABLE `users` CHANGE `ID`  `id` INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `users` DROP FOREIGN KEY `fk_Users_Roles1`;
ALTER TABLE users DROP INDEX fk_Users_Roles1_idx;
ALTER TABLE `users` DROP `Roles_RoleId`;
ALTER TABLE `users` ADD `role` VARCHAR(255) NOT NULL;
DROP TABLE roles;
DROP TABLE places_has_photos;
ALTER TABLE `priceincludes` CHANGE `description`  `description` VARCHAR(128)
CHARACTER SET utf8
COLLATE utf8_general_ci NOT NULL;
ALTER TABLE `orders` ADD `discountInformation` TEXT NOT NULL;
ALTER TABLE `comments` ADD INDEX (`id`);
ALTER TABLE `comments` ADD `next_comment_id` INT UNSIGNED NULL
AFTER `tour_id`;
ALTER TABLE `comments` ADD INDEX (`next_comment_id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`next_comment_id`) REFERENCES `quicktour`.`comments` (
  `id`
)
  ON DELETE SET NULL
  ON UPDATE CASCADE;
/*Update roles*/
UPDATE `quicktour`.`users`
SET `role` = 'admin'
WHERE `users`.`id` IN (3, 4);
UPDATE `quicktour`.`users`
SET `role` = 'agent'
WHERE `users`.`id` IN (2, 5, 6);
UPDATE `quicktour`.`users`
SET `role` = 'user'
WHERE `users`.`id` IN (7, 8, 9, 10);

#  Tour photo relation
ALTER TABLE `tours` DROP `MainPhotoUrl`;
ALTER TABLE `tours` ADD `photo_id` INT NULL
AFTER `TransportDesc`;
ALTER TABLE `tours` ADD INDEX (`photo_id`);
ALTER TABLE `tours` ADD FOREIGN KEY (`photo_id`) REFERENCES `quicktour`.`photos` (
  `id`
)
  ON DELETE SET NULL
  ON UPDATE CASCADE;
ALTER TABLE `tours` ADD UNIQUE (
  `photo_id`
);

INSERT INTO `photos` (`url`)
VALUES (
  "1.jpg"
), (
  "2.jpg"
), (
  "3.jpg"
), (
  "4.jpg"
), (
  "5.jpg"
), (
  "6.jpg"
), (
  "7.jpg"
), (
  "8.jpg"
), (
  "9.jpg"
), (
  "10.jpg"
);
UPDATE `quicktour`.`tours`
SET `photo_id` = '6'
WHERE `tours`.`ToursId` = 1;
UPDATE `quicktour`.`tours`
SET `photo_id` = '7'
WHERE `tours`.`ToursId` = 2;
UPDATE `quicktour`.`tours`
SET `photo_id` = '8'
WHERE `tours`.`ToursId` = 3;
UPDATE `quicktour`.`tours`
SET `photo_id` = '9'
WHERE `tours`.`ToursId` = 4;
UPDATE `quicktour`.`tours`
SET `photo_id` = '10'
WHERE `tours`.`ToursId` = 5;
UPDATE `quicktour`.`tours`
SET `photo_id` = '11'
WHERE `tours`.`ToursId` = 6;
UPDATE `quicktour`.`tours`
SET `photo_id` = '12'
WHERE `tours`.`ToursId` = 7;
UPDATE `quicktour`.`tours`
SET `photo_id` = '13'
WHERE `tours`.`ToursId` = 8;
UPDATE `quicktour`.`tours`
SET `photo_id` = '14'
WHERE `tours`.`ToursId` = 9;
UPDATE `quicktour`.`tours`
SET `photo_id` = '15'
WHERE `tours`.`ToursId` = 10;
# Create comment-tour relation
ALTER TABLE `comments` DROP FOREIGN KEY `comments_ibfk_1`;

ALTER TABLE `comments` ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `quicktour`.`users` (
  `ID`
)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE `comments` ADD INDEX (`tour_id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`tour_id`) REFERENCES `quicktour`.`tours` (
  `ToursId`
)
  ON DELETE CASCADE
  ON UPDATE CASCADE;