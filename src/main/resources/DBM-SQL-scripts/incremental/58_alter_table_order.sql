ALTER TABLE `quicktour`.`orders`
CHANGE COLUMN `price` `price` DECIMAL(10, 2) NULL DEFAULT NULL,
CHANGE COLUMN `discount` `discount` DECIMAL(5, 2) NULL DEFAULT NULL,
ADD COLUMN `Accepted_date` DATETIME NULL
AFTER `Users_ID`,
ADD COLUMN `Confirmed_date` DATETIME NULL
AFTER `Accepted_date`,
ADD COLUMN `Completed_date` DATETIME NULL
AFTER `Confirmed_date`,
ADD COLUMN `Cancelled_date` DATETIME NULL
AFTER `Completed_date`;