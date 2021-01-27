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
    max_capacity double precision,
    primary key (id),
    foreign key (truck_id) references trucks(id)
);

create table order_details (
    id bigint,
    version int,
    remaining_path varchar(200),
    remaining_working_time double precision,
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
                                     ('Ekaterinburg'), ('Chelabinsk'), ('Omsk'), ('Novosibirsk');

insert into roads(city_a_code, city_b_code, distance_in_hours)
values (1,2,5),(1,3,7),(1,9,12),
       (2,3,3),(2,5,9),(2,7,8),
       (3,1,7),(3,2,3),(3,4,4),(3,5,8),(3,6,4),(3,7,7),
       (4,3,4),(4,6,3),(4,9,4),
       (5,2,10),(5,3,8),(5,7,2),
       (6,3,4),(6,4,3),(6,7,5),(6,8,3),
       (7,2,10),(7,3,7),(7,5,2),(7,6,5),
       (8,6,3),(8,9,4),(8,11,3),
       (9,1,12),(9,4,4),(9,8,4),(9,10,2),(9,11,3),
       (10,9,2),(10,11,3),(10,12,7),(10,13,11),
       (11,8,3),(11,9,3),(11,10,3),(11,12,6),
       (12,10,7),(12,11,6),(12,13,5),
        (13,10,11),(13,12,5);

insert into trucks(id, shift_size, capacity, serviceable, city_code, version)
values ('AB12345', 2, 30, true, 1, 0), ('BA12345', 2, 26, false, 11, 0),
       ('GH12345', 3, 46, true, 4, 0), ('MK12345', 2, 25, false, 7, 0),
       ('CD12345', 1, 27, true, 3, 0), ('DC12345', 1, 21, true, 5, 0);

insert into users(version, email, password, enable, role)
values (0,'alex.m@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true, 'DRIVER'),
       (0,'vasia.g@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC',  true,'DRIVER'),
       (0,'olga.p@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'petr.f@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'katy.p@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'gosha.k@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true, 'DRIVER'),
       (0,'roman.l@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC',  true,'DRIVER'),
       (0,'boris.m@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'petr.z@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'nadeshda.b@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       (0,'admin1@gmail.com', '$2y$12$81QxgdqO0B8Tb8Qo61urdudm7G38VRU8MYV0iFdGkiRrD9wbRar3a', true,'EMPLOYEE'),
       (0,'admin2@gmail.com', '$2y$12$81QxgdqO0B8Tb8Qo61urdudm7G38VRU8MYV0iFdGkiRrD9wbRar3a', true,'EMPLOYEE');


insert into drivers(id, first_name, last_name, hours_worked, driver_status, city_code, truck_id)
values (1, 'Alex', 'Matushkin', '160', 'REST', 1, null ),
       (2, 'Vasia', 'Grigoriev', '66', 'REST', 2, null),
       (3 ,'Olga', 'Petrova', '11', 'REST', 3, null ),
       (4, 'Petr', 'Frolov', '175', 'REST', 11, null),
       (5, 'Katy', 'Pery', '0', 'REST', 12, null),
       (6, 'Gosha', 'Kalinin', '0', 'REST', 5, null ),
       (7, 'Roman', 'Litvinov', '130', 'REST', 7, null),
       (8 ,'Boris', 'Mayakovsky', '121', 'REST', 7, null ),
       (9, 'Petr', 'Zvonov', '96', 'REST', 11, null),
       (10, 'Nadeshda', 'Babkina', '17', 'REST', 12, null);

insert into work_shifts(id, active)
values (1, false),(2, false),(3, false),
       (4, false),(5, false),(6, false),
       (7, false),(8, false),(9, false),
       (10, false);

insert into cargo(title, weight, cargo_status, version)
values ('Titanium', 12000, 'PREPARED', 0), ('Wood', 6300, 'PREPARED', 0), ('Clay', 8700, 'PREPARED', 0),
       ('Magnesite', 5900, 'PREPARED',0), ('Gravel', 10600, 'PREPARED', 0), ('Sand', 6100, 'PREPARED', 0),
       ('Dolomite', 2200, 'PREPARED', 0), ('Gypsum', 700, 'PREPARED', 0), ('Calcareous Tuffs', 1500, 'PREPARED', 0),
       ('Quartzite', 3200, 'PREPARED',0), ('Marble', 8600, 'PREPARED', 0), ('Granite', 8700, 'PREPARED', 0),
       ('Lead', 9100, 'PREPARED', 0), ('Tin', 4800, 'PREPARED', 0);



