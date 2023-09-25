drop table if exists tasks;

create table tasks (
    id uuid default gen_random_uuid() primary key,
    name varchar(256) not null unique,
    description varchar(10240),
    creation_date timestamp without time zone,
    modification_date timestamp without time zone,
    is_done boolean
);

insert into tasks (name, description, creation_date, is_done)
values ('sample task 1', 'sample description', CURRENT_TIMESTAMP, false);

insert into tasks (name, description, creation_date, is_done)
values ('sample task 2', 'sample description 2', CURRENT_TIMESTAMP, false);

insert into tasks (name, description, creation_date, is_done)
values ('sample task 3', 'sample description 3', CURRENT_TIMESTAMP, false);

insert into tasks (name, description, creation_date, is_done)
values ('sample task 4', 'sample description 4', CURRENT_TIMESTAMP, false);