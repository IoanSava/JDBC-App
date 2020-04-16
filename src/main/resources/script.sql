-- create a new database called MusicAlbums
create database MusicAlbums;

-- create a new user
create user dba with password 'sql';


create table artists(
    id integer not null generated always as identity,
    name varchar(100) not null,
    country varchar(100),
    primary key (id)
);

create table albums(
    id integer not null generated always as identity,
    name varchar(100) not null,
    artist_id integer not null references artists on delete restrict,
    release_year integer,
    primary key (id)
);

create table charts (
    id integer not null generated always as identity,
    name varchar(100) not null,
    primary key (id)
);

insert into charts(name) values('New ranking');

create table charts_albums (
    id integer not null generated always as identity,
    chart_id integer not null references charts on delete restrict,
    album_id integer not null references albums on delete restrict,
    rank integer not null,
    primary key (id),
    unique(chart_id, album_id),
    unique(chart_id, rank)
);