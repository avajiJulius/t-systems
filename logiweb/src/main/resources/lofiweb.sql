drop table if exists cargo_waypoints;
drop table if exists waypoints;
drop table if exists cargo;
drop table if exists orders;
drop table if exists drivers;
drop table if exists trucks;
drop table if exists country_map;
drop table if exists roads;
drop table if exists cities;

create table cities (
    city_code serial,
    city_name varchar(100),
    primary key(city_code)
);

insert into cities(city_name) values ('SPb'), ('Moscow'), ('Vladivostok');

create table roads (
    road_id serial,
    city_a_code integer,
    city_b_code integer,
    distance double precision,
    primary key (city_a_code),
    foreign key (city_a_code) references cities(city_code),
    foreign key (city_b_code) references cities(city_code)
);

insert into roads(city_a_code, city_b_code, distance)
values (1, 2, 250), (2,1, 250), (3,1, 700), (2,3, 580);


create table country_map (
    city_code integer,
    road_id integer,
    primary key (road_id)
);


create table trucks (
    truck_id serial,
    work_shift_size double precision,
    capacity double precision,
    serviceable boolean,
    city_code integer,
    primary key (truck_id),
    foreign key (city_code) references cities(city_code)
);

insert into trucks(work_shift_size, capacity, serviceable, city_code)
values (2, 2000, true, 1), ( 0 , 3000, false , null),
       (1, 4000, true, 3), ( 0, 5000, true, 2);

create table drivers (
    driver_id varchar(7),
    first_name varchar(50),
    last_name varchar(50),
    worked_time_in_hours double precision,
    driver_status varchar(50),
    city_code integer,
    truck_id integer,
    primary key(driver_id),
    foreign key (city_code) references cities(city_code),
    foreign key (truck_id) references trucks(truck_id)
);

insert into drivers(driver_id, first_name, last_name, worked_time_in_hours, driver_status, city_code, truck_id)
values ('AB12345', 'Alex', 'Matushkin', '160', 'IN_SHIFT', 1, 1),
       ('BA12345', 'Vasia', 'Grigoriev', '60', 'REST', 2, null),
       ('CD12345', 'Olya', 'Petrova', '10', 'IN_SHIFT', 3, 3),
       ('DC12345', 'Petya', 'Frolov', '175', 'IN_SHIFT', 1, 1);

create table orders (
    order_id serial,
    completed boolean,
    truck_id integer,
    primary key (order_id),
    foreign key (truck_id) references trucks(truck_id)
);

insert into orders(completed, truck_id)
values (false , 1), (false , 3);

create table cargo (
    cargo_id serial,
    title varchar(50),
    weight double precision,
    cargo_status varchar(50),
    primary key (cargo_id)
);

insert into cargo(title, weight, cargo_status)
values ('A', '100', 'PREPARED'), ('B', '200', 'PREPARED'), ('C', '300', 'PREPARED'),
       ('D', '400', 'PREPARED'), ('E', '500', 'PREPARED'), ('F', '600', 'PREPARED');


create table waypoints (
    waypoint_id serial,
    city_code integer,
    type varchar(30),
    order_id integer,
    cargo_id integer,
    primary key (waypoint_id),
    foreign key (city_code) references cities(city_code),
    foreign key (order_id) references orders(order_id),
    foreign key (cargo_id) references cargo(cargo_id)
);

insert into waypoints(city_code, type, order_id, cargo_id)
values (1, 'LOADING', 1, 1), (1, 'LOADING', 1, 2), (3, 'LOADING', 1, 3), (2, 'UNLOADING', 1, 1),(2, 'UNLOADING', 1, 2),(2, 'UNLOADING', 1, 3),
       (3, 'LOADING', 2, 4),(3, 'LOADING', 2, 5),(3, 'LOADING', 2, 6),(2, 'UNLOADING', 2, 5),(1, 'UNLOADING', 2, 4),(1, 'UNLOADING', 2, 6);


-- create table cargo_waypoints (
--      waypoint_id integer,
--      cargo_id integer,
--      primary key(waypoint_id, cargo_id)
-- );
--
-- insert into cargo_waypoints(waypoint_id, cargo_id)
-- values (1,1), (1,2), (2, 3), (3, 1), (3, 2) ,(3, 3),
--        (4,4), (4,5), (4, 6), (5, 5), (6, 4) ,(6, 6);


