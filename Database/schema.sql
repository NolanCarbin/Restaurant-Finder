BEGIN TRANSACTION;
create table restaurant (
	restaurant_id serial PRIMARY KEY NOT NULL,
	name VARCHAR(150) NOT NULL,
	location VARCHAR(250) NOT NULL,
	rating decimal(2,1),
	price_id integer,
	url VARCHAR(200)
);

create table price (
    price_id INT PRIMARY KEY,
    money_sign VARCHAR(4)
);


ALTER TABLE restaurant ADD FOREIGN KEY (price_id) REFERENCES price(price_id);
COMMIT;


INSERT INTO price (price_id, money_sign) VALUES (1, '$'), (2, '$$'), (3, '$$$'), (4, '$$$$');