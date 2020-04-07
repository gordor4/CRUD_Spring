create schema clients;
create table clients.clients
(
    id serial primary key,
    risk_profile varchar(6) not null check (risk_profile in ('LOW', 'NORMAL', 'HIGH'))
)