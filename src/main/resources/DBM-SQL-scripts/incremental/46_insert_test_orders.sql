UPDATE `quicktour`.`orders` SET `status`='Accepted' WHERE `id`='1';
UPDATE `quicktour`.`orders` SET `status`='Confirmed' WHERE `id`='2';
UPDATE `quicktour`.`orders` SET `status`='Received' WHERE `id`='3';
INSERT INTO `quicktour`.`orders` (`order_date`, `number_of_adults`, `number_of_children`, `user_info`, `price`, `discount`, `next_payment_date`, `status`, `TourId`, `Companies_id`, `Users_ID`)
  VALUES ('2013-12-10 00:00:00', '1', '2', 'User info 1334', '2000', '10', '2013-12-15', 'Received', '4', '2', '8'),
         ('2013-11-20 00:00:00', '3', '0', 'User info 2134', '1500', '0', '2013-11-25', 'Received', '5', '2', '9'),
         ('2013-12-15 00:00:00', '4', '4', 'User info 689', '3000', '15', '2013-12-18', 'Completed', '6', '2', '10'),
         ('2013-12-18 00:00:00', '2', '1', 'User info 888', '800', '10', '2013-12-21', 'Confirmed', '1', '1', '7'),
         ('2013-12-28 00:00:00', '4', '0', 'User info 3244', '1000', '5', '2013-12-30', 'Confirmed', '1', '1', '10'),
         ('2013-12-18 00:00:00', '2', '2', 'User info 3255', '1200', '10', '2013-12-20', 'Completed', '5', '2', '7');
