CREATE TABLE brand (
	brand_id int4 NOT NULL,
	created_at timestamp NULL,
	last_updated timestamp NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT brand_pkey PRIMARY KEY (brand_id)
);

CREATE TABLE country (
	country_id int4 NOT NULL,
	createdat timestamp NULL,
	last_updated timestamp NULL,
	"name" varchar(255) NULL,
	CONSTRAINT country_pkey PRIMARY KEY (country_id)
);

CREATE TABLE car (
	uuid varchar(255) NOT NULL,
	created_at timestamp NULL,
	last_updated timestamp NULL,
	registration timestamp NOT NULL,
	brand_id int4 NULL,
	country_id int4 NULL,
	CONSTRAINT car_pkey PRIMARY KEY (uuid),
	CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(country_id),
	CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);

CREATE SEQUENCE public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 100
	CACHE 1
	NO CYCLE;

