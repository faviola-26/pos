INSERT INTO product_category(id, name) values (null, 'footware');
INSERT INTO product_category(id, name) values (null, 'kids');
INSERT INTO product_category(id, name) values (null, 'woman');
INSERT INTO product_category(id, name) values (null, 'man');
INSERT INTO product_category(id, name) values (null, 'sports');
INSERT INTO product_category(id, name) values (null, 'converse');

INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 2);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 3);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 4);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (4, 5);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (5, 6);

INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Color', 4, '#<hex_num>', 1, 'shoe color');
INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Size', 3, '<number>.<half-number>', 3, 'shoe size');
INSERT INTO product_characteristic(id, name, type, format, value_interpreter, description) values (null, 'Season', 6, 'season_name', 1, 'season of the year');

INSERT INTO product(id, name, description, category_id) values (1, 'classic', 'classic model', 6);
