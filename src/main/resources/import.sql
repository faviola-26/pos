INSERT INTO product_category(id, name) values (null, 'footware');
INSERT INTO product_category(id, name) values (null, 'kids');
INSERT INTO product_category(id, name) values (null, 'woman');
INSERT INTO product_category(id, name) values (null, 'man');
INSERT INTO product_category(id, name) values (null, 'sports');
INSERT INTO product_category(id, name) values (null, 'converse');
INSERT INTO product_category(id, name) values (null, 'casual');
INSERT INTO product_category(id, name) values (null, 'flexi');
INSERT INTO product_category(id, name) values (null, 'sandals');
INSERT INTO product_category(id, name) values (null, 'flip flops h&m');

INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 2);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 3);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 4);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (4, 5);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (5, 6);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (3, 7);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (7, 8);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (2, 9);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (9, 10);

INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Color', 4, '#<hex_num>', 1, 'shoe color');
INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Size', 3, '<number>.<half-number>', 3, 'shoe size');
INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Season', 6, 'season_name', 1, 'season of the year');

INSERT INTO product(id, name, description, category_id) values (1, 'black converse', 'classic model converse', 6);
INSERT INTO product(id, name, description, category_id) values (1, 'classic woman', 'classic high heel', 8);
INSERT INTO product(id, name, description, category_id) values (1, 'fresh beach', 'blue flipflops', 10);

INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('black', 1, 1);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('3-6', 1, 2);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('all-year', 1, 3);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('black', 2, 1);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('4-7', 2, 2);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('party', 2, 3);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('blue', 3, 1);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('1-3', 3, 2);
INSERT INTO assinged_characteristic_product(_value, product_id, characteristic_id) values ('vacations', 3, 3);


