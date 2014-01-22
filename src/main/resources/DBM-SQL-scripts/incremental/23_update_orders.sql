UPDATE `orders` SET order_date = '2013-11-10 00:00:00',
  number_of_adults = 1,
  number_of_children = 0,
  user_info = 'info',
  price = 200,
  discount = 10,
  status = 'new'
WHERE id = 1;

UPDATE `orders` SET order_date = '2013-10-01 00:00:00',
  number_of_adults = 1,
  number_of_children = 2,
  user_info = 'info',
  price = 500,
  discount = 5,
  status = 'new'
WHERE id = 2;

UPDATE `orders` SET order_date = '2013-09-10 00:00:00',
  number_of_adults = 3,
  number_of_children = 0,
  user_info = 'info',
  price = 400,
  discount = 0,
  status = 'new'
WHERE id = 3;