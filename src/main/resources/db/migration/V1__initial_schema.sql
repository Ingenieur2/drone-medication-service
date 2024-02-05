create table drone
(
    serial_number    varchar(100)    not null primary key,
    model            varchar(25)     not null,
    weight           numeric(10, 3)  not null,
    battery_capacity numeric(6, 3)   not null,
    state            varchar(25)     not null
);
insert into drone (serial_number, model, weight, battery_capacity, state)
values ('serial_1', 'LIGHT_WEIGHT', 300.342, 98.476, 'LOADING');
insert into drone (serial_number, model, weight, battery_capacity, state)
values ('serial_2', 'CRUISER_WEIGHT', 500.000, 100.000, 'IDLE');

create table medication
(
    id      uuid            not null primary key,
    name    varchar(100)    not null,
    weight  numeric(10, 3)  not null,
    code    varchar(100)    not null,
    image   text
);
insert into medication (id, name, weight, code)
values ('3422b448-2460-4fd2-9183-8000de6f8343','water', 70.123, 'FRSFT_TE45'),
 ('3422b448-2460-4fd2-9183-8000de6f8100','acid', 170.325, 'FRSFT_DR78');

create table current_loading_information (
    serial_number   varchar(100)    not null,
    id              uuid            not null
);
insert into current_loading_information (serial_number, id)
values ('serial_1', '3422b448-2460-4fd2-9183-8000de6f8343');

create table drone_state_history (
	serial_number varchar(100) not null,
	battery_capacity numeric(6, 3)   not null,
	create_dt timestamp
);