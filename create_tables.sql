
CREATE DATABASE challenge;

CREATE TABLE percentage_records
(
    id         serial
        constraint percentage_records_pk primary key,
    type       varchar(50),
    value      varchar(250),
    created_at timestamp with time zone
);


