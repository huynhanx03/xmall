-- Create the database
CREATE DATABASE xmall_product_db;
USE xmall_product_db;

-- Table: Product_Attribute
-- Stores attributes (e.g., "capacity", "weight") and their type (0: Sales Attribute, 1: Regular Attribute)
CREATE TABLE Product_Attribute (
    attribute_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type INT NOT NULL CHECK (type IN (0, 1)) -- 0: Sales Attribute, 1: Regular Attribute
);

-- Table: Category
-- Stores product categories (e.g., "Smartphones")
CREATE TABLE Category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- Table: Brand
-- Stores brands (e.g., "Apple")
CREATE TABLE Brand (
    brand_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- Table: SPU (Standard Product Unit)
-- Represents a type of product (e.g., iPhone 12 Pro Max)
CREATE TABLE SPU (
    spu_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    brand_id INT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES Category(category_id),
    FOREIGN KEY (brand_id) REFERENCES Brand(brand_id)
);

-- Table: SKU (Stock Keeping Unit)
-- Represents a specific variant of an SPU (e.g., iPhone 12 Pro Max Gold 256GB)
CREATE TABLE SKU (
    sku_id INT PRIMARY KEY AUTO_INCREMENT,
    spu_id INT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (spu_id) REFERENCES SPU(spu_id)
);

-- Table: SKU_Sales_Attribute_Value
-- Links SKUs to their Sales Attribute values (e.g., capacity = 256GB)
CREATE TABLE SKU_Sales_Attribute_Value (
    sku_id INT,
    attribute_id INT,
    value VARCHAR(100) NOT NULL,
    PRIMARY KEY (sku_id, attribute_id),
    FOREIGN KEY (sku_id) REFERENCES SKU(sku_id),
    FOREIGN KEY (attribute_id) REFERENCES Product_Attribute(attribute_id),
    CONSTRAINT check_sales_attribute CHECK (
        (SELECT type FROM Product_Attribute WHERE attribute_id = SKU_Sales_Attribute_Value.attribute_id) = 0
    )
);

-- Table: SKU_Regular_Attribute_Value
-- Links SKUs to their Regular Attribute values (e.g., weight = 228g)
CREATE TABLE SKU_Regular_Attribute_Value (
    sku_id INT,
    attribute_id INT,
    value VARCHAR(100) NOT NULL,
    PRIMARY KEY (sku_id, attribute_id),
    FOREIGN KEY (sku_id) REFERENCES SKU(sku_id),
    FOREIGN KEY (attribute_id) REFERENCES Product_Attribute(attribute_id),
    CONSTRAINT check_regular_attribute CHECK (
        (SELECT type FROM Product_Attribute WHERE attribute_id = SKU_Regular_Attribute_Value.attribute_id) = 1
    )
);

-- Table: SPU_Image
-- Stores images for SPUs
CREATE TABLE SPU_Image (
    spu_image_id INT PRIMARY KEY AUTO_INCREMENT,
    spu_id INT,
    image_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (spu_id) REFERENCES SPU(spu_id)
);

-- Table: SKU_Image
-- Stores images for SKUs
CREATE TABLE SKU_Image (
    sku_image_id INT PRIMARY KEY AUTO_INCREMENT,
    sku_id INT,
    image_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (sku_id) REFERENCES SKU(sku_id)
);

-- Insert Example Data

-- Insert Product Attributes
INSERT INTO Product_Attribute (name, type) VALUES 
('capacity', 0), -- Sales Attribute
('color', 0),    -- Sales Attribute
('weight', 1);   -- Regular Attribute

-- Insert Category
INSERT INTO Category (name) VALUES ('Smartphones');

-- Insert Brand
INSERT INTO Brand (name) VALUES ('Apple');

-- Insert SPU
INSERT INTO SPU (category_id, brand_id, name, description) VALUES 
(1, 1, 'iPhone 12 Pro Max', 'A high-end smartphone by Apple');

-- Insert SKU
INSERT INTO SKU (spu_id, name, price) VALUES 
(1, 'iPhone 12 Pro Max Gold 256GB', 1099.99),
(1, 'iPhone 12 Pro Max Gold 512GB', 1199.99);

-- Insert SKU Sales Attribute Values
INSERT INTO SKU_Sales_Attribute_Value (sku_id, attribute_id, value) VALUES 
(1, 1, '256GB'), -- capacity
(1, 2, 'Gold'),  -- color
(2, 1, '512GB'), -- capacity
(2, 2, 'Gold');  -- color

-- Insert SKU Regular Attribute Values
INSERT INTO SKU_Regular_Attribute_Value (sku_id, attribute_id, value) VALUES 
(1, 3, '228g'), -- weight
(2, 3, '228g'); -- weight

-- Insert SPU Image
INSERT INTO SPU_Image (spu_id, image_url) VALUES 
(1, 'http://example.com/iphone12promax.jpg');

-- Insert SKU Image
INSERT INTO SKU_Image (sku_id, image_url) VALUES 
(1, 'http://example.com/iphone12promax_gold_256gb.jpg'),
(2, 'http://example.com/iphone12promax_gold_512gb.jpg');