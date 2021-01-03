drop table if exists work_details;
drop table if exists cargo_waypoints;
drop table if exists waypoints;
drop table if exists cargo;
drop table if exists orders;
drop table if exists drivers;
drop table if exists employee;
drop table if exists users_roles;
drop table if exists roles;
drop table if exists work_shifts;
drop table if exists users;
drop table if exists trucks;
drop table if exists country_map;
drop table if exists roads;
drop table if exists cities;

create table cities (
    city_code bigserial,
    city_name varchar(100),
    primary key(city_code)
);

insert into cities(city_name) values ('SPb'), ('Moscow'), ('Vladivostok');

create table roads (
    road_id bigserial,
    city_a_code bigint,
    city_b_code bigint,
    distance_in_hours double precision,
    primary key (road_id),
    foreign key (city_a_code) references cities(city_code),
    foreign key (city_b_code) references cities(city_code)
);

insert into roads(city_a_code, city_b_code, distance_in_hours)
values (1, 2, 5), (2 , 1, 5), (3,1, 20),(1,3, 20), (2,3, 15),(3,2, 15);


create table country_map (
    city_code bigint,
    road_id bigint,
    primary key (road_id, city_code)
);

create table trucks (
    truck_id varchar(7),
    shift_size int default 0,
    capacity double precision,
    serviceable boolean default true,
    city_code bigint,
    primary key (truck_id),
    foreign key (city_code) references cities(city_code)
);

insert into trucks(truck_id, shift_size, capacity, serviceable, city_code)
values ('AB12345', 2, 10000, true, 1), ('BA12345',2 , 3000, false, 1),
       ('CD12345',1, 20000, true, 3), ('DC12345', 1, 5000, true, 2);



create table users(
    id bigserial,
    email varchar(30) unique,
    password varchar(100),
    enable boolean default true,
    role varchar(30),
    primary key (id)
);

insert into users(email, password, enable, role)
values ('avaji@gmail.com', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true, 'DRIVER'),
       ('vas', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC',  true,'DRIVER'),
       ('olga', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
       ('petr', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
        ('test', '$2y$12$AvXxA6mEE6cDVFsyWGHg/.W1Ot1OHA18F15dwRjjJOQbqeOGdVDEC', true,'DRIVER'),
        ('admin@gmail.com', '$2y$12$81QxgdqO0B8Tb8Qo61urdudm7G38VRU8MYV0iFdGkiRrD9wbRar3a', true,'EMPLOYEE');

create table work_shifts (
    id bigint,
    active boolean default false,
    shift_start timestamp default null,
    shift_end timestamp default null,
    primary key(id),
    foreign key (id) references users(id)
);

insert into work_shifts (id, active)
values (1 ,false);

create table drivers (
    id bigint,
    first_name varchar(50),
    last_name varchar(50),
    hours_worked double precision default 0,
    driver_status varchar(50),
    city_code bigint,
    truck_id varchar(7) default null,
    primary key(id),
    foreign key (id) references users(id),
    foreign key (city_code) references cities(city_code),
    foreign key (truck_id) references trucks(truck_id)
);


insert into drivers(id, first_name, last_name, hours_worked, driver_status, city_code, truck_id)
values (1, 'Alex', 'Matushkin', '160', 'DRIVING', 1, 'AB12345'),
       (2, 'Vasia', 'Grigoriev', '60', 'REST', 2, null),
       (3 ,'Olya', 'Petrova', '10', 'DRIVING', 3, 'CD12345'),
       (4, 'Petya', 'Frolov', '175', 'SECOND_DRIVER', 1, 'AB12345'),
       (5, 'TEST', 'TESTOVICH', '0', 'REST', 1, null);



create table orders (
    order_id bigserial,
    completed boolean default false,
    truck_id varchar(7) default null,
    primary key (order_id),
    foreign key (truck_id) references trucks(truck_id)
);

insert into orders(completed, truck_id)
values (false , 'AB12345'), (false , 'CD12345');

create table cargo (
    cargo_id bigserial,
    title varchar(50),
    weight double precision,
    cargo_status varchar(50),
    primary key (cargo_id)
);

insert into cargo(title, weight, cargo_status)
values ('A', '100', 'PREPARED'), ('B', '200', 'PREPARED'), ('C', '300', 'PREPARED'),
       ('D', '400', 'PREPARED'), ('E', '500', 'PREPARED'), ('F', '600', 'PREPARED');


create table waypoints (
    waypoint_id bigserial,
    city_code bigint,
    type varchar(30),
    order_id bigint,
    cargo_id bigint,
    primary key (waypoint_id),
    foreign key (city_code) references cities(city_code),
    foreign key (order_id) references orders(order_id),
    foreign key (cargo_id) references cargo(cargo_id)
);

insert into waypoints(city_code, type, order_id, cargo_id)
values (1, 'LOADING', 1, 1), (1, 'LOADING', 1, 2), (3, 'LOADING', 1, 3), (2, 'UNLOADING', 1, 1),(2, 'UNLOADING', 1, 2),(2, 'UNLOADING', 1, 3),
       (3, 'LOADING', 2, 4),(3, 'LOADING', 2, 5),(3, 'LOADING', 2, 6),(2, 'UNLOADING', 2, 5),(1, 'UNLOADING', 2, 4),(1, 'UNLOADING', 2, 6);


create table work_details (
    id bigserial,
    truck_id varchar(7),
    order_id bigint,
    shift_id bigint,
    primary key (id),
    foreign key (id) references users(id),
    foreign key (truck_id) references trucks(truck_id),
    foreign key (order_id) references orders(order_id),
    foreign key (shift_id) references work_shifts(id)
);

insert into work_details(id, truck_id, order_id, shift_id)
values (1, 'AB12345', 1, 1);

