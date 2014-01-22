ALTER TABLE `tours_discount_policy` DROP FOREIGN KEY `fk_Tours_has_discount_policy_discount_policy1`;

ALTER TABLE `tours_discount_policy` ADD CONSTRAINT `fk_Tours_has_discount_policy_discount_policy1` FOREIGN KEY (`discount_policy_id`) REFERENCES `quicktour`.`discount_policy` (
  `id`
)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE `discount_policy` CHANGE `cond`  `cond` TEXT NULL DEFAULT NULL;
