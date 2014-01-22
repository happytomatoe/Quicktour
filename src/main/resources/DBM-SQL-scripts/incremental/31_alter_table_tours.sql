alter table tours
add company_id int(11);

alter table tours
add foreign key (company_id) references companies(id);

update tours set company_id=2 where toursid=1;
update tours set company_id=2 where toursid=2;
update tours set company_id=2 where toursid=3;