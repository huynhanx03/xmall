-- Create the database
CREATE
DATABASE xmall_product_db;
USE
xmall_product_db;

-- Table: product_attribute
-- Stores attributes (e.g., "capacity", "weight") and their type (0: Sales Attribute, 1: Regular Attribute)
CREATE TABLE product_attribute
(
    attribute_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the attribute',
    name         VARCHAR(100) NOT NULL COMMENT 'Name of the attribute (e.g., capacity, weight)',
    type         TINYINT UNSIGNED NOT NULL CHECK (type IN (0, 1)) COMMENT '0: Sales Attribute, 1: Regular Attribute',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time'
) COMMENT='Stores product attributes and their type';

-- Table: category
-- Stores product categories (e.g., "Smartphones")
CREATE TABLE category
(
    category_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the category',
    name        VARCHAR(100) NOT NULL COMMENT 'Name of the category',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time'
) COMMENT='Stores different product categories';

-- Table: brand
-- Stores brands (e.g., "Apple")
CREATE TABLE brand
(
    brand_id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the brand',
    name       VARCHAR(100) NOT NULL COMMENT 'brand name',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time'
) COMMENT='Stores different product brands';

-- Table: spu (Standard Product Unit)
-- Represents a type of product (e.g., iPhone 12 Pro Max)
CREATE TABLE spu
(
    spu_id      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the spu',
    category_id BIGINT UNSIGNED NOT NULL COMMENT 'category ID to which this product belongs',
    brand_id    BIGINT UNSIGNED NOT NULL COMMENT 'brand ID of this product',
    name        VARCHAR(100) NOT NULL COMMENT 'Name of the product',
    description TEXT COMMENT 'Detailed description of the product',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (category_id) REFERENCES category (category_id),
    FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
) COMMENT='Represents a standard product unit (spu)';

-- Table: sku (Stock Keeping Unit)
-- Represents a specific variant of an spu (e.g., iPhone 12 Pro Max Gold 256GB)
CREATE TABLE sku
(
    sku_id     BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the sku',
    spu_id     BIGINT UNSIGNED NOT NULL COMMENT 'spu ID to which this sku belongs',
    name       VARCHAR(100)   NOT NULL COMMENT 'Name of the specific product variant',
    price      DECIMAL(10, 2) NOT NULL CHECK (price >= 0) COMMENT 'Price of the sku',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (spu_id) REFERENCES spu (spu_id)
) COMMENT='Represents a specific stock-keeping unit (sku)';

-- Table: sku_sales_attribute_value
-- Links skus to their Sales Attribute values (e.g., capacity = 256GB)
CREATE TABLE sku_sales_attribute_value
(
    sku_id       BIGINT UNSIGNED NOT NULL COMMENT 'sku ID associated with this attribute',
    attribute_id BIGINT UNSIGNED NOT NULL COMMENT 'Attribute ID representing a sales attribute',
    value        VARCHAR(100) NOT NULL COMMENT 'Value of the sales attribute (e.g., 256GB, Gold)',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    PRIMARY KEY (sku_id, attribute_id),
    FOREIGN KEY (sku_id) REFERENCES sku (sku_id),
    FOREIGN KEY (attribute_id) REFERENCES product_attribute (attribute_id)
) COMMENT='Links skus to their sales attributes and values';

-- Table: sku_regular_attribute_value
-- Links skus to their Regular Attribute values (e.g., weight = 228g)
CREATE TABLE sku_regular_attribute_value
(
    sku_id       BIGINT UNSIGNED NOT NULL COMMENT 'sku ID associated with this attribute',
    attribute_id BIGINT UNSIGNED NOT NULL COMMENT 'Attribute ID representing a regular attribute',
    value        VARCHAR(100) NOT NULL COMMENT 'Value of the regular attribute (e.g., 228g)',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    PRIMARY KEY (sku_id, attribute_id),
    FOREIGN KEY (sku_id) REFERENCES sku (sku_id),
    FOREIGN KEY (attribute_id) REFERENCES product_attribute (attribute_id)
) COMMENT='Links skus to their regular attributes and values';

-- Table: spu_image
-- Stores images for spus
CREATE TABLE spu_image
(
    spu_image_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the spu image',
    spu_id       BIGINT UNSIGNED NOT NULL COMMENT 'spu ID associated with this image',
    image_url    VARCHAR(255) NOT NULL COMMENT 'URL of the spu image',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (spu_id) REFERENCES spu (spu_id)
) COMMENT='Stores images related to spus';

-- Table: sku_image
-- Stores images for skus
CREATE TABLE sku_image
(
    sku_image_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the sku image',
    sku_id       BIGINT UNSIGNED NOT NULL COMMENT 'sku ID associated with this image',
    image_url    VARCHAR(255) NOT NULL COMMENT 'URL of the sku image',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (sku_id) REFERENCES sku (sku_id)
) COMMENT='Stores images related to skus';

-- Triggers for enforcing attribute type constraints
DELIMITER $$
CREATE TRIGGER check_sales_attribute
    BEFORE INSERT
    ON sku_sales_attribute_value
    FOR EACH ROW
BEGIN
    DECLARE attr_type TINYINT;
    SELECT type INTO attr_type FROM product_attribute WHERE attribute_id = NEW.attribute_id;
    IF attr_type IS NULL OR attr_type <> 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: attribute_id must be a Sales Attribute (type = 0)';
END IF;
END $$

CREATE TRIGGER check_regular_attribute
    BEFORE INSERT
    ON sku_regular_attribute_value
    FOR EACH ROW
BEGIN
    DECLARE attr_type TINYINT;
    SELECT type INTO attr_type FROM product_attribute WHERE attribute_id = NEW.attribute_id;
    IF attr_type IS NULL OR attr_type <> 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: attribute_id must be a Regular Attribute (type = 1)';
END IF;
END $$
DELIMITER ;

-- Insert Product Attributes
INSERT INTO product_attribute (name, type)
VALUES ('capacity', 0), -- Sales Attribute
       ('color', 0),    -- Sales Attribute
       ('weight', 1);
-- Regular Attribute

-- Insert category
INSERT INTO category (name)
VALUES ('Smartphones');

-- Insert brand
INSERT INTO brand (name)
VALUES ('Apple');

-- Insert spu
INSERT INTO spu (category_id, brand_id, name, description)
VALUES (1, 1, 'iPhone 12 Pro Max', 'A high-end smartphone by Apple');

-- Insert sku
INSERT INTO sku (spu_id, name, price)
VALUES (1, 'iPhone 12 Pro Max Gold 256GB', 1099.99),
       (1, 'iPhone 12 Pro Max Gold 512GB', 1199.99);

-- Insert sku Sales Attribute Values
INSERT INTO sku_sales_attribute_value (sku_id, attribute_id, value)
VALUES (1, 1, '256GB'), -- capacity
       (1, 2, 'Gold'),  -- color
       (2, 1, '512GB'), -- capacity
       (2, 2, 'Gold');
-- color

-- Insert sku Regular Attribute Values
INSERT INTO sku_regular_attribute_value (sku_id, attribute_id, value)
VALUES (1, 3, '228g'), -- weight
       (2, 3, '228g');
-- weight

-- Insert spu Image
INSERT INTO spu_image (spu_id, image_url)
VALUES (1, 'http://example.com/iphone12promax.jpg');

-- Insert sku Image
INSERT INTO sku_image (sku_id, image_url)
VALUES (1, 'http://example.com/iphone12promax_gold_256gb.jpg'),
       (2, 'http://example.com/iphone12promax_gold_512gb.jpg');
