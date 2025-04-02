-- Create the database for Warehouse Service
CREATE DATABASE xmall_warehouse_db;
USE xmall_warehouse_db;

-- Table: Inventory
-- Tracks stock levels for each SKU
CREATE TABLE Inventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    sku_id INT NOT NULL, -- References SKU from Product Service
    stock_quantity INT NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert Example Data
INSERT INTO Inventory (sku_id, stock_quantity) VALUES 
(1, 100), -- iPhone 12 Pro Max Gold 256GB
(2, 50);  -- iPhone 12 Pro Max Gold 512GB