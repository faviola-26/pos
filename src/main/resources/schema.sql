/**
 * Author:  faviola
 * Created: 2 may 2020
 */
CREATE SCHEMA IF NOT EXISTS POS;

CREATE TABLE IF NOT EXISTS category(
    id SMALLINT AUTO_INCREMENT PRIMARY KEY NOT NULL, 
    name VARCHAR(50) NOT NULL,
    ancestor SMALLINT,
    FOREIGN KEY(ancestor) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS category_subcategories(
    parent_id SMALLINT NOT NULL,
    child_id SMALLINT NOT NULL,
    FOREIGN KEY(parent_id) REFERENCES category(id),
    FOREIGN KEY(child_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS characteristic(
    id SMALLINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(60) NOT NULL UNIQUE,
    type INT NOT NULL,
    format VARCHAR(15) NOT NULL,
    valueInterpreter INT NOT NULL,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    category SMALLINT,
    FOREIGN KEY(category) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS product_characteristic(
    _value VARCHAR(50) NOT NULL,
    product BIGINT,
    characteristic SMALLINT,
    FOREIGN KEY(characteristic) REFERENCES characteristic(id),
    FOREIGN KEY(product) REFERENCES product(id)
);