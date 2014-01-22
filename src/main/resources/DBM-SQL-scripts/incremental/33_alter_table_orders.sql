USE quicktour;
ALTER TABLE `orders`ADD `next_payment_date` DATE AFTER `discount`;
