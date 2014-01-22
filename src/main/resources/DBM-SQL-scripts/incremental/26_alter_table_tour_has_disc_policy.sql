USE quicktour;
RENAME TABLE	tours_has_discount_policy TO tours_discount_policy;
ALTER TABLE tours_discount_policy DROP PRIMARY  KEY;
ALTER TABLE  `tours_discount_policy` ADD  `id` INT NOT NULL AUTO_INCREMENT FIRST ,
ADD PRIMARY KEY (`id`) ;