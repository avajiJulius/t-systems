drop table if exists cities;
drop table if exists roads;
drop table if exists trucks;
drop table if exists orders;
drop table if exists cargo;
drop table if exists waypoints;

create table cities (
    city_code serial,
    city_name varchar(100),
    primary key(city_code)
);

create table roads (
    road_id serial,
   city_a_code integer,
   city_b_code integer,
   distance double precision,
   primary key (city_a_code),
   foreign key (city_a_code) references cities(city_code),
   foreign key (city_b_code) references cities(city_code)
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

create table drivers (
    driver_id serial,
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

create table orders (
    order_id serial,
    completed boolean,
    truck_id integer,
    primary key (order_id),
    foreign key (truck_id) references trucks(truck_id)
);

create table cargo (
    cargo_id serial,
    title varchar(50),
    weight double precision,
    cargo_status varchar(50),
    primary key (cargo_id)
);

create table waypoints (
    waypoint_id serial,
    city_code integer,
    cargo_id integer,
    type varchar(30),
    order_id integer,
    primary key (waypoint_id),
    foreign key (city_code) references cities(city_code),
    foreign key (cargo_id) references cargo(cargo_id),
    foreign key (order_id) references orders(order_id)
);




