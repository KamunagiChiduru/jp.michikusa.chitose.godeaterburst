drop table if exists item_category;
drop table if exists item_info;
create table item_category (id  integer, category varchar not null, category_id bigint not null, primary key (id));
create table item_info (id  integer, category_id bigint not null, name varchar not null, nitems integer, buy_amount integer, sell_amount integer, note varchar, primary key (id));
