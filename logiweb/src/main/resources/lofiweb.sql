drop table if exists work_details;
drop table if exists cargo_waypoints;
drop table if exists waypoints;
drop table if exists cargo;
drop table if exists orders;
drop table if exists drivers;
drop table if exists work_shifts;
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
    distance_in_hours double precision,
    primary key (road_id),
    foreign key (city_a_code) references cities(city_code),
    foreign key (city_b_code) references cities(city_code)
);

insert into roads(city_a_code, city_b_code, distance_in_hours)
values (1, 2, 5), (2 , 1, 5), (3,1, 20),(1,3, 20), (2,3, 15),(3,2, 15);


create table country_map (
    city_code integer,
    road_id integer,
    primary key (road_id, city_code)
);

create table trucks (
    truck_id varchar(7),
    shift_size integer default 0,
    capacity double precision,
    serviceable boolean,
    city_code integer,
    primary key (truck_id),
    foreign key (city_code) references cities(city_code)
);

insert into trucks(truck_id, shift_size, capacity, serviceable, city_code)
values ('AB12345', 2, 10000, true, 1), ('BA12345',0 , 3000, false, 1),
       ('CD12345',1, 20000, true, 3), ('DC12345', 0, 5000, true, 2);

create table work_shifts (
    shift_id serial,
    active boolean default false,
    shift_start timestamp default null,
    shift_end timestamp default null,
    primary key(shift_id)
);

create table drivers (
    driver_id serial,
    first_name varchar(50),
    last_name varchar(50),
    hours_worked double precision default 0,
    driver_status varchar(50),
    city_code integer,
    truck_id varchar(7) default null,
    shift_id integer default null,
    primary key(driver_id),
    foreign key (city_code) references cities(city_code),
    foreign key (truck_id) references trucks(truck_id),
    foreign key (shift_id) references work_shifts(shift_id)
);


insert into drivers(first_name, last_name, hours_worked, driver_status, city_code, truck_id)
values ('Alex', 'Matushkin', '160', 'DRIVING', 1, 'AB12345'),
       ('Vasia', 'Grigoriev', '60', 'REST', 2, null),
       ('Olya', 'Petrova', '10', 'DRIVING', 3, 'CD12345'),
       ('Petya', 'Frolov', '175', 'SECOND_DRIVER', 1, 'AB12345'),
       ('TEST', 'TESTOVICH', '0', 'REST', 1, null);



create table orders (
    order_id serial,
    completed boolean default false,
    truck_id varchar(7) default null,
    primary key (order_id),
    foreign key (truck_id) references trucks(truck_id)
);

insert into orders(completed, truck_id)
values (false , 'AB12345'), (false , 'CD12345');

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


create table work_details (
    id serial,
    driver_id integer,
    truck_id varchar(7),
    order_id integer,
    shift_id integer,
    primary key (id),
    foreign key (driver_id) references drivers(driver_id),
    foreign key (truck_id) references trucks(truck_id),
    foreign key (order_id) references orders(order_id),
    foreign key (shift_id) references work_shifts(shift_id)
);