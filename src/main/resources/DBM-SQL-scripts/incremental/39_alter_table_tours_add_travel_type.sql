alter table tours
add travel_type enum('driving','walking','transit','bycicling');

update tours set travel_type='driving' where toursid=2;