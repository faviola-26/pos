/**
 * Author:  faviola
 * Created: 4 may 2020
 */

INSERT INTO category(id, name, ancestor) values (1, 'footware', null);
INSERT INTO category(id, name, ancestor) values (2, 'kids', 1);
INSERT INTO category(id, name, ancestor) values (3, 'woman', 1);
INSERT INTO category(id, name, ancestor) values (4, 'man', 1);

INSERT INTO category_subcategories(parent_id, child_id) values (1, 2);
INSERT INTO category_subcategories(parent_id, child_id) values (1, 3);
INSERT INTO category_subcategories(parent_id, child_id) values (1, 4);
