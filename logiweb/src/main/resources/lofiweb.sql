create table trucks (
    truck_id serial,
    work_shift_size double precision,
    capacity double precision,
    serviceable boolean,
    current_city varchar(100),
    primary key (truck_id)
);
