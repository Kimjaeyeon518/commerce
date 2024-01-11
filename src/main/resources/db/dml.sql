insert into member(member_id, username, password, name, role, point)
values(1, "jae518@naver.com", "$2a$10$K2pLuKqJRt72WJdC5nPKxOL5tSFaFM7aCL5SYRKQ0XAkbg9L4Ai0O", "재연", "CUSTOMER", 1000000);

insert into member(member_id, username, password, name, role, point)
values(2, "jae5181@naver.com", "wodus123", "재연", "CUSTOMER", 1000000);

insert into member(member_id, username, password, name, role, point)
values(3, "jae5182@naver.com", "wodus123", "재연", "CUSTOMER", 1000000);

insert into member(member_id, username, password, name, role, point)
values(4, "jae5183@naver.com", "wodus123", "재연", "CUSTOMER", 1000000);

insert into product(product_id, store_id, name, category, description, price, amount, sale_count)
values(1, 1, "상품1", "OUTER", "", 1000, 10000, 0);

insert into orders(order_id, member_id, status, total_price, address1, address2, zip_code, created_at, updated_at)
values(1, 1, "WAITING_PAY", 10000, "서울시", "강남구", "06251", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into order_line(order_line_id, order_id, product_id, amount)
values(1, 1, 1, 10);

insert into orders(order_id, member_id, status, total_price, address1, address2, zip_code, created_at, updated_at)
values(2, 2, "WAITING_PAY", 10000, "서울시", "강남구", "06252", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into order_line(order_line_id, order_id, product_id, amount)
values(2, 2, 1, 10);

insert into orders(order_id, member_id, status, total_price, address1, address2, zip_code, created_at, updated_at)
values(3, 1, "WAITING_PAY", 10000, "서울시", "강남구", "06251", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into order_line(order_line_id, order_id, product_id, amount)
values(3, 3, 1, 10);

insert into orders(order_id, member_id, status, total_price, address1, address2, zip_code, created_at, updated_at)
values(4, 2, "WAITING_PAY", 10000, "서울시", "강남구", "06252", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into order_line(order_line_id, order_id, product_id, amount)
values(4, 4, 1, 10);

insert into store(store_id, member_id)
values(1, 1);