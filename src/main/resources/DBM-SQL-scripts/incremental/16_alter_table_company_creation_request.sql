alter table company_creation_requests
drop primary key ;
alter table company_creation_requests
add Id int(10) unsigned not null auto_increment primary key;