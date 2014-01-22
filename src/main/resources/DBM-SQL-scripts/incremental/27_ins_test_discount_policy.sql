USE quicktour;
INSERT INTO `quicktour`.`discount_policy` (`id`, `name`, `formula`, `description`, `startDate`, `endDate`, `cond`, `Companies_id`)
VALUES (NULL, 'New Year Discount', '3', 'New Year discount', '2014-01-01', '2014-01-01', NULL, '2'),
  (NULL, 'Monday discount', '5', 'Dear customers!We provide you discount 5% on every tour every Monday', NULL, NULL,
   'DayOfWeek is Monday', '2'),
  (NULL, 'Regular customer discount', 'numbOfOrders*0.01*100',
   'Every person that has more than 12 orders in our company will have discount numbOfOrders*0.01*100 % on every tour',
   NULL, NULL, 'numbOfOrders>12', '2');

