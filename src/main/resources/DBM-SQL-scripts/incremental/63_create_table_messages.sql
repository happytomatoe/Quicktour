create table comments
(
comment_id int(11) unsigned not null AUTO_INCREMENT,
user_id int(11) not null,
tour_id int(11) not null,
comment varchar(2000),
comment_date TIMESTAMP,
primary key(comment_id),
foreign key(user_id) references users(id)
);