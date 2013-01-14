drop table if exists item_info;
drop table if exists users;
create table item_info (id  integer, category varchar not null, name varchar not null, nitems integer, buy_amount integer, sell_amount integer, note varchar, primary key (id));
create table users (id  integer, name varchar, password varchar, primary key (id));
