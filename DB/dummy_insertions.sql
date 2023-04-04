show databases;

use springproject;

select * from user;

insert into user
(username, password, role, email, address, phone)
values
('bob', '123', 'CUSTOMER', 'bob@gmail.com', '444', '999-999-9999'),
('ray', '123', 'CUSTOMER', 'ray@gmail.com', '555', '999-999-9999'),
('may', '123', 'CUSTOMER', 'may@gmail.com', '555', '999-999-9999');

select * from purchase_details;

describe purchase_details;
describe purchase;
describe product;

select * from product;


-- inserting data into category
describe category;
select * from category;

insert into category
(name)
values
('fruit'),
('veggies'),
('beer'),
('soft drink');

/*
13	beer
12	fruit
15	soft drink
14	veggies
*/

-- inserting dummy data into product
describe product;
select * from product;
insert into product
(name, description, imagePath, categoryId, price, salesCount, isOnSale, discountPercent)
values
('coke', 'a dark beverage', 'some/path/on/my/machine', 15, 4, 100, 0, 0),
('sprite', 'a transparent beverage', 'another/path/on/my/machine', 15, 3, 100, 0, 0),
('mountain dew', 'an orange beverage', 'a/path/somewhere', 15, 4.5, 100, 0, 0),
('dr pepper', 'drink for Texans', 'this/path/', 15, 4, 100, 0, 0),
('budweiser', 'to beer or not to beer', 'from/the/can/', 13, 5, 1000, 0, 0),
('stella artois', 'an imported beer', 'from/the/other/side/of/the/atlantic', 13, 8, 100, 0, 0),
('apple', 'an apple a day keeps the doctor awaw', 'from/the/orchard', 12, 1, 1000, 0, 0),
('pear', 'yellow fruit', 'also/from/the/orchard', 12, 2, 200, 0, 0),
('plum', 'to make pudding', 'there/it/is', 12, 1, 200, 0, 0),
('tomato', 'fruit or veggie?', 'from/the/garden', 14, 2, 100, 0, 0),
('cucumber', 'green, long', 'somewhere/over/the/rainbow', 14, 1, 100, 0, 0),
('pepper', 'no description', 'no/path', 14, 1.5, 100, 0, 0),
('chili pepper', 'this stuff is hot', 'some/path', 14, 2.5, 100, 0, 0),
('lime', 'last one', 'erqlkjr', 12, 0.5, 1000, 0, 0);

/*
18	coke	a dark beverage	some/path/on/my/machine	15	4.00	100	0	0
19	sprite	a transparent beverage	another/path/on/my/machine	15	3.00	100	0	0
20	mountain dew	an orange beverage	a/path/somewhere	15	4.50	100	0	0
21	dr pepper	drink for Texans	this/path/	15	4.00	100	0	0
22	budweiser	to beer or not to beer	from/the/can/	13	5.00	1000	0	0
23	stella artois	an imported beer	from/the/other/side/of/the/atlantic	13	8.00	100	0	0
24	apple	an apple a day keeps the doctor awaw	from/the/orchard	12	1.00	1000	0	0
25	pear	yellow fruit	also/from/the/orchard	12	2.00	200	0	0
26	plum	to make pudding	there/it/is	12	1.00	200	0	0
27	tomato	fruit or veggie?	from/the/garden	14	2.00	100	0	0
28	cucumber	green, long	somewhere/over/the/rainbow	14	1.00	100	0	0
29	pepper	no description	no/path	14	1.50	100	0	0
30	chili pepper	this stuff is hot	some/path	14	2.50	100	0	0
31	lime	last one	erqlkjr	12	0.50	1000	0	0
*/

-- inserting dummy data to test sql query
select * from purchase;
insert into purchase
(id, userId, timeStamp, total_price)
values
(8,3,'2023-04-02 03:00:00',10),
(2,5,'2023-04-02 00:00:00',24),
(3,6,'2023-04-02 00:00:00',24),
(4,7,'2023-04-02 00:00:00',24),
(5,7,'2023-04-02 01:00:00',27);
/*
id	userId	timeStamp				total_price	
1	3	2023-04-02 00:00:00.000000	24.00
2	5	2023-04-02 00:00:00.000000	24.00
3	6	2023-04-02 00:00:00.000000	24.00
4	7	2023-04-02 00:00:00.000000	24.00
5	7	2023-04-02 01:00:00.000000	27.00
8	3	2023-04-02 03:00:00.000000	10.00
*/

-- inserting dummy data into purchase details
select * from purchase_details;
describe purchase_details;

insert into purchase_details
(id, purchaseId, productId, quantity, price, isOnSale, discountPercent)
values
(100, 1, 18, 1, 4, 0, 0),
(101, 1, 19, 2, 6, 0, 0),
(102, 2, 21, 3, 12, 0, 0),
(103, 2, 22, 1, 5, 0, 0),
(104, 2, 30, 2, 5, 0, 0),
(105, 3, 31, 10, 5, 0, 0);

insert into purchase_details
(id, purchaseId, productId, quantity, price, isOnSale, discountPercent)
values
(106, 4, 18, 1, 4, 0, 0),
(107, 5, 19, 2, 6, 0, 0),
(108, 5, 21, 3, 12, 0, 0),
(109, 8, 22, 1, 5, 0, 0),
(110, 8, 30, 2, 5, 0, 0),
(111, 8, 31, 10, 5, 0, 0);


insert into purchase_details
(id, purchaseId, productId, quantity, price, isOnSale, discountPercent)
values
(112, 1, 18, 3, 12, 0, 0),
(113, 1, 19, 1, 3, 0, 0),
(114, 3, 25, 3, 6, 0, 0),
(115, 4, 25, 3, 6, 0, 0),
(116, 8, 25, 3, 6, 0, 0),
(117, 8, 27, 10, 20, 0, 0);

-- testing
