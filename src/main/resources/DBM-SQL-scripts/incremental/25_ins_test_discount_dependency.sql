USE quicktour;
INSERT INTO `quicktour`.`discount_dependency` (`id`, `tag`, `table_field`, `description`, `basedOn`)
VALUES (NULL, 'n', 'order.count', 'umber of orders of selected user', 'user'),
  (NULL, 't', 'order.times', 'Number of times that user took selected tour', 'user');
