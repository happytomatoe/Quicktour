TRUNCATE discount_dependency;
INSERT INTO `discount_dependency` (`id`, `tag`, `table_field`, `description`) VALUES
(1, 'adult', 'orders.number_of_adults', 'Number of adults in order'),
(2, 'age', 'users.age', 'User''s age');