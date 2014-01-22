USE quicktour;
ALTER TABLE `discount_policy`
  DROP `name`,
  DROP `formula`,
  DROP `description`,
  DROP `condition` ;
ALTER TABLE  `discount_policy` ADD  `name` VARCHAR( 50 ) NOT NULL AFTER  `id` ,
ADD  `formula` VARCHAR( 50 ) NOT NULL AFTER  `name` ,
ADD  `description` TEXT NOT NULL AFTER  `name` ,
ADD  `cond` VARCHAR(50) NULL DEFAULT NULL AFTER  `description` ;
ALTER TABLE  `discount_policy` ADD UNIQUE (
`name`
);