alter table places change geowidth geowidth double signed;
alter table places change geoheight geoheight double signed;
update places set geoheight=37.966667, geowidth=23.716667 where placeid=1;
update places set geoheight=36.923548, geowidth=21.68518 where placeid=2;
update places set geoheight=40.742055, geowidth=-74.013748 where placeid=3;
update places set geoheight=41.896144, geowidth=-87.628555 where placeid=4;
update places set geoheight=43.032761, geowidth=-87.902527 where placeid=5;
update places set geoheight=50.073888, geowidth=19.944191 where placeid=6;
update places set geoheight=52.237472, geowidth=21.009865 where placeid=7;
