create table lecture
(
    id   bigint auto_increment primary key,
    name varchar(100),
    term varchar(100)
);

create table term
(
    id   bigint auto_increment primary key,
    name varchar(100)
);

create table term_lectures (
    term bigint,
    lecture bigint
);