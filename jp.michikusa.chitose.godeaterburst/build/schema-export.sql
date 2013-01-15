drop table if exists item_category;
drop table if exists item_info;
create table item_category (id  integer, name varchar not null, primary key (id), unique (name));
create table item_info (id  integer, category_id bigint not null, name varchar not null, limit_numbers integer, purchasing_price integer, selling_price integer, note varchar, primary key (id), unique (category_id, name));
