/*
 * Author:  faviola
 * Created: 2 may 2020
 */
CREATE SCHEMA IF NOT EXISTS POS;

CREATE TABLE IF NOT EXISTS product_category(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL, 
    name VARCHAR(50) NOT NULL,
    ancestor_id BIGINT,
    FOREIGN KEY(ancestor_id) REFERENCES product_category(id)
);

CREATE TABLE IF NOT EXISTS product_category_sub_categories(
    category_id BIGINT NOT NULL,
    sub_categories_id BIGINT NOT NULL,
    FOREIGN KEY(category_id) REFERENCES product_category(id),
    FOREIGN KEY(sub_categories_id) REFERENCES product_category(id)
);

CREATE TABLE IF NOT EXISTS product_characteristic(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(60) NOT NULL UNIQUE,
    type INT NOT NULL,
    format VARCHAR(15) NOT NULL,
    value_interpreter INT NOT NULL,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    category_id BIGINT,
    FOREIGN KEY(category_id) REFERENCES product_category(id)
);

CREATE TABLE IF NOT EXISTS assinged_characteristic_product(
    _value VARCHAR(50) NOT NULL,
    product_id BIGINT,
    characteristic_id INT,
    FOREIGN KEY(characteristic_id) REFERENCES product_characteristic(id),
    FOREIGN KEY(product_id) REFERENCES product(id)
);
