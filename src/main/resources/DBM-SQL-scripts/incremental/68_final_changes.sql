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
WHERE `users`.`id` IN (1, 2, 5, 6);
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

# Change passwords to SHA-256
UPDATE `quicktour`.`users`
SET `password` = '7a9b4bd9948d7a47e824f873f72e371254850fa7284b2ec2d6beb048246ed981b9c9e20493e137f4'
WHERE `users`.`id` = 1;
UPDATE `quicktour`.`users`
SET `password` = '74ec0c44deec96ba486de709ec327932f43c0c40ae0d2c0c286528f23ae61e92eb57762ef3a06925'
WHERE `users`.`id` = 2;
UPDATE `quicktour`.`users`
SET `password` = '9b93c8bf2241cf090ecf95a16914d82987d0a0f2bd4a380f5a907558844d896f0c9eaac8e574b288'
WHERE `users`.`id` = 3;
UPDATE `quicktour`.`users`
SET `password` = 'c018ea78cf1602d6bc5ff3d94462b332bd1c2c099caab19a6910fa160e5ebeb6fcfc5eee605d96b3'
WHERE `users`.`id` = 4;
UPDATE `quicktour`.`users`
SET `password` = '091fd45e668251e235831fbceaa76630cfeede62c968dca8ea340dd54c3f5990cf5cec733f744a64'
WHERE `users`.`id` = 5;
UPDATE `quicktour`.`users`
SET `password` = '4c59e5b9cb5a491ff5c1fedd7254a345b35af67ff40bbd7b39ec8bde390cc3a7f6c2f7ff9a26dab8'
WHERE `users`.`id` = 6;
UPDATE `quicktour`.`users`
SET `password` = '55ff08400ab98e506e2ab58b3e62215dd1774776fa2f0dd3d94170456995d9b6a6f93b41823c92b5'
WHERE `users`.`id` = 7;
UPDATE `quicktour`.`users`
SET `password` = '3a4e3ccd5f2937f1e179e9a015a7f26892e997399c33862cfd8e9fd459e4a88f0c73191d9aa4c66e'
WHERE `users`.`id` = 8;
UPDATE `quicktour`.`users`
SET `password` = '2eaf7abd06b27207ef710b08b996454349f8fa2b49021249118cbb83e0b20a3f43cd0bd2a7a907db'
WHERE `users`.`id` = 9;
UPDATE `quicktour`.`users`
SET `password` = '4f1f27ec51ab9f12c62ba54ce5e041de144f6611bf83666ed33db05984fa83ede6b1c6fc0f0fd1fb'
WHERE `users`.`id` = 10;
