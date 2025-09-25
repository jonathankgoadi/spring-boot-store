INSERT INTO categories (name)
VALUES ('Smartphones'),
       ('Laptops'),
       ('Tablets'),
       ('Headphones'),
       ('Smartwatches');


INSERT INTO products (name, description, price, category_id)
VALUES
-- Smartphones
('iPhone 15 Pro', 'Apple smartphone with A17 Pro chip and 48MP camera.', 999.00, 1),
('Samsung Galaxy S24 Ultra', 'Flagship Android phone with 200MP camera and S Pen.', 1199.00, 1),

-- Laptops
('MacBook Air M2', '13-inch Apple laptop with M2 chip and 18-hour battery life.', 1099.00, 2),
('Dell XPS 15', 'High-performance Windows laptop with Intel i7 and 1TB SSD.', 1499.99, 2),

-- Tablets
('iPad Pro 12.9"', 'Apple iPad with M4 chip and Liquid Retina XDR display.', 1299.00, 3),
('Samsung Galaxy Tab S9', '11-inch Android tablet with AMOLED display.', 799.00, 3),

-- Headphones
('Sony WH-1000XM5', 'Wireless noise-canceling headphones with 30-hour battery.', 399.99, 4),
('Apple AirPods Pro (2nd Gen)', 'Wireless earbuds with active noise cancellation.', 249.00, 4),

-- Smartwatches
('Apple Watch Series 9', 'Advanced health-tracking smartwatch with GPS.', 399.00, 5),
('Samsung Galaxy Watch 6', 'Smartwatch with fitness tracking and AMOLED display.', 329.00, 5);
