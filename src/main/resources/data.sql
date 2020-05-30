
INSERT INTO product_category(id, name, ancestor_id) values (null, 'footware', null);
INSERT INTO product_category(id, name, ancestor_id) values (null, 'kids', 1);
INSERT INTO product_category(id, name, ancestor_id) values (null, 'woman', 1);
INSERT INTO product_category(id, name, ancestor_id) values (null, 'man', 1);

INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 2);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 3);
INSERT INTO product_category_sub_categories(category_id, sub_categories_id) values (1, 4);
