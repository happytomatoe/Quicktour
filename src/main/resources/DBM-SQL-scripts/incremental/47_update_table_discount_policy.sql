DELETE FROM `quicktour`.`discount_policy`;
INSERT INTO `discount_policy` (`id`, `name`, `description`, `cond`, `formula`, `startDate`, `endDate`, `Companies_id`)
VALUES
  (4, 'Grown up discount', 'Discount for grown ups!', 'users.age>20', '3', NULL, NULL, 2),
  (5, 'Free discount', '<p>Discount for everyone!!</p>\r\n', NULL, '5', NULL, NULL, 2);
INSERT INTO `quicktour`.`tours_discount_policy` (
  `id`,
  `Tours_ToursId`,
  `discount_policy_id`
)
VALUES (
  NULL, '1', '4'
), (
  NULL, '1', '5'
);
INSERT INTO `quicktour`.`tours_discount_policy` (
  `id`,
  `Tours_ToursId`,
  `discount_policy_id`
)
VALUES (
  NULL, '2', '4'
);