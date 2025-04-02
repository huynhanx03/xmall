-- Create the database for Order Service
CREATE DATABASE order_db;
USE order_db;

-- Table: Orders
-- Stores customer orders
CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL, -- References a customer (assumed to be managed elsewhere)
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL
);

-- Table: Order_Items
-- Stores items in each order
CREATE TABLE Order_Items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    sku_id INT NOT NULL, -- References SKU from Product Service
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Insert Example Data
INSERT INTO Orders (customer_id, total_amount) VALUES 
(1, 2199.98); -- Example order for customer 1

INSERT INTO Order_Items (order_id, sku_id, quantity, unit_price) VALUES 
(1, 1, 2, 1099.99); -- 2 units of iPhone 12 Pro Max Gold 256GB