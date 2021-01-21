drop table if exists waypoints;
drop table if exists cargo;
drop table if exists work_shifts;
drop table if exists drivers;
drop table if exists users;
drop table if exists order_details;
drop table if exists orders;
drop table if exists trucks;
drop table if exists country_map;
drop table if exists roads;
drop table if exists cities;

create table cities (
    city_code bigserial,
    city_name varchar(100),
    primary key(city_code)
);

create table roads (
    road_id bigserial,
    city_a_code bigint,
    city_b_code bigint,
    distance_in_hours double precision,
    primary key (road_id),
    foreign key (city_a_code) references cities(city_code),
    foreign key (city_b_code) references cities(city_code)
);

create table country_map (
    city_code bigint,
    road_id bigint,
    primary key (city_code, road_id),
    foreign key (city_code) references cities(city_code),
    foreign key (road_id) references roads(road_id)
);

create table trucks (
    id varchar(7),
    version int,
    shift_size int default 0,
    capacity double precision,
    serviceable boolean default true,
    city_code bigint not null,
    primary key (id),
    foreign key (city_code) references cities(city_code)
);

create table orders (
    id bigserial,
    version int,
    completed boolean default false,
    path varchar,
    truck_id varchar(7) default null,
    primary key (id),
    foreign key (truck_id) references trucks(id)
);

create table order_details (
    id bigint,
    version int,
    remaining_path varchar(30),
    primary key (id),
    foreign key (id) references orders(id)
);


create table users (
    id bigserial,
    version int,
    email varchar(30) unique,
    password varchar(100),
    enable boolean default true,
    role varchar(30),
    primary key (id)
);

create table drivers (
    id bigint,
    first_name varchar(50),
    last_name varchar(50),
    hours_worked double precision default 0,
    driver_status varchar(50),
    city_code bigint,
    truck_id varchar(7),
    order_details bigint,
    primary key(id),
    foreign key (id) references users(id),
    foreign key (city_code) references cities(city_code),
    foreign key (truck_id) references trucks(id),
    foreign key (order_details) references order_details(id)
);

create table work_shifts (
    id bigint,
    active boolean default false,
    shift_start timestamp default null,
    shift_end timestamp default null,
    primary key(id),
    foreign key (id) references drivers(id)
);

create table cargo (
    id bigserial,
    version int,
    title varchar(50),
    weight double precision,
    cargo_status varchar(50),
    primary key (id)
);

create table waypoints (
    waypoint_id bigserial,
    version int,
    city_code bigint,
    type varchar(30),
    order_id bigint,
    cargo_id bigint,
    primary key (waypoint_id),
    foreign key (city_code) references cities(city_code),
    foreign key (order_id) references orders(id),
    foreign key (cargo_id) references cargo(id)
);

insert into cities(city_name) values ('Saint-Petersburg'), ('Moscow'), ('Nizhniy-Novgorod'),
                                    ('Kazan'), ('Rostov-na-Donu'), ('Samara'),
                                     ('Volgograd'), ('Ufa'), ('Perm'),
                                     ('Ekaterinburg'), ('Chelabinsk'), ('Omsk');

insert into roads(city_a_code, city_b_code, distance_in_hours)
values (1,2,5),(1,3,7),
       (2,3,3),(2,5,10),(2,7,10),
       (3,1,7),(3,2,3),(3,4,4),(3,5,8),(3,6,4),(3,7,7),
       (4,3,4),(4,6,3),(4,9,4),
       (5,2,10),(5,3,8),(5,7,2),
       (6,3,4),(6,4,3),(6,7,5),(6,8,3),
       (7,2,10),(7,3,7),(7,5,2),(7,6,5),
       (8,6,3),(8,9,4),(8,11,3),
       (9,4,4),(9,8,4),(9,10,2),(9,11,3),
       (10,9,2),(10,11,3),(10,12,7),
       (11,8,3),(11,9,3),(11,10,3),(11,12,6),
       (12,10,7),(12,11,6);

insert into country_map(city_code, road_id)
values (1,1),(1,2),
       (2,1),(2,3),(2,4),(2,5),
       (3,2),(3,3),(3,6),(3,8),(3,10),(3,11),
       (4,10),(4,12),(4,13),
       (5,4),(5,6),(5,7),
       (6,9),(6,11),(6,12),(6,14),
       (7,2),(7,5),(7,7),(7,8),(7,9),
       (8,14),(8,15),(8,18),
       (9,13),(9,15),(9,16),(9,19),
       (10,16),(10,17),(10,21),
       (11,17),(11,18),(11,19),(11,20),
       (12,21),(12,20);

insert into trucks(id, version, shift_size, capacity, serviceable, city_code)
values ('AB12345', 1,2, 30, true, 1), ('BA12345',1, 2, 26, false, 1),
       ('CD12345', 1, 1, 27, true, 3), ('DC12345', 1,1, 21, true, 2);

insert into users(version, email, password, enable, role)
values (1,'avaji@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true, 'DRIVER'),
       (1,'vas', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC',  true,'DRIVER'),
       (1,'olga', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (1,'petr', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (1,'test', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (1,'admin@gmail.com', '$2y$12$81QxgdqO0B8Tb8Qo61urdudm7G38VRU8MYV0iFdGkiRrD9wbRar3a', true,'EMPLOYEE');


insert into drivers(id, first_name, last_name, hours_worked, driver_status, city_code, truck_id)
values (1, 'Alex', 'Matushkin', '160', 'REST', 1, null ),
       (2, 'Vasia', 'Grigoriev', '60', 'REST', 2, null),
       (3 ,'Olya', 'Petrova', '10', 'REST', 3, null ),
       (4, 'Petya', 'Frolov', '175', 'REST', 1, null),
       (5, 'TEST', 'TESTOVICH', '0', 'REST', 1, null);

insert into work_shifts(id, active)
values (1, false);

insert into cargo(title, weight, cargo_status, version)
values ('Metal', '12', 'PREPARED',1), ('Wood', '8.5', 'PREPARED',1), ('Furniture', '5', 'PREPARED', 1),
       ('Master Peace', '0.3', 'PREPARED',1), ('Gravel', '10.6', 'PREPARED', 1), ('Sand', '6.1', 'PREPARED', 1);



