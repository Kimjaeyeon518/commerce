drop table if exists order_line cascade;
drop table if exists orders cascade;
drop table if exists product cascade;
drop table if exists member cascade;
drop table if exists store cascade;
drop table if exists favorite cascade;
drop table if exists cart cascade;

create table order_line (
    amount bigint,
    order_id bigint not null,
    order_line_id bigint not null auto_increment,
    product_id bigint not null,
    primary key (order_line_id)
);

create table orders (
    created_at timestamp(6) not null,
    member_id bigint,
    order_id bigint not null auto_increment,
    total_price bigint,
    updated_at timestamp(6) not null,
    address1 varchar(255),
    address2 varchar(255),
    status varchar(255) check (status in ('WAITING_PAY','COMPLETE','CONFIRMED','CANCEL')),
    zip_code varchar(255),
    primary key (order_id)
);

create table product (
     amount bigint,
     price bigint,
     product_id bigint not null auto_increment,
     sale_count bigint,
     store_id bigint not null,
     category varchar(255) check (category in ('OUTER','TOP','BOTTOM','SHOES')),
     description varchar(255),
     name varchar(255),
     primary key (product_id)
);

create table member (
    member_id bigint not null auto_increment,
    point bigint,
    name varchar(255),
    password varchar(255),
    role varchar(255) check (role in ('CUSTOMER','ADMIN')),
    username varchar(255) unique,
    primary key (member_id)
);

create table store (
   member_id bigint not null,
   store_id bigint not null auto_increment,
   primary key (store_id)
);

-- create table favorite
-- (
--     favorite_id bigint not null auto_increment,
--     member_id   bigint not null,
--     product_id  bigint not null,
-- );
--
-- create table cart (
--     amount bigint,
--     favorite_id bigint not null auto_increment,
--     member_id bigint not null,
--     product_id bigint not null,
--     primary key (favorite_id)
-- );