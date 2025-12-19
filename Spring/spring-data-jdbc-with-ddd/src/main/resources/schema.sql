create table lecture
(
    id   bigint auto_increment primary key,
    version bigint,
    name varchar(100),
    term_id bigint
);

create table term
(
    id   bigint auto_increment primary key,
    version bigint,
    name varchar(100),
    limit_of_lecture integer
);

create table term_lectures (
    term bigint,
    lecture bigint
);

create table registration (
    lecture bigint,
    student bigint
);

create table student (
    id   bigint auto_increment primary key,
    name varchar(100)
);