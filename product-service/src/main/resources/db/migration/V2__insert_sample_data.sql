-- ==========================================
-- CATEGORIES
-- ==========================================
INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic gadgets'),
('Fashion', 'Clothing and accessories'),
('Home & Kitchen', 'Home appliances')
ON CONFLICT (name) DO NOTHING;

-- ==========================================
-- PRODUCTS (ALL 7 SKUs)
-- ==========================================
INSERT INTO products (name, description, price, sku, category_id, created_at, updated_at)
VALUES
('iPhone 17', 'Flagship smartphone', 1099.00, 'SKU-1001',
    (SELECT id FROM categories WHERE name='Electronics'), NOW(), NOW()),
('MacBook Air', 'Lightweight laptop', 1599.00, 'SKU-1002',
    (SELECT id FROM categories WHERE name='Electronics'), NOW(), NOW()),
('AirPods Pro', 'Wireless earbuds', 299.00, 'SKU-1003',
    (SELECT id FROM categories WHERE name='Electronics'), NOW(), NOW()),

('Running Shoes', 'Sport running shoes', 89.00, 'SKU-2001',
    (SELECT id FROM categories WHERE name='Fashion'), NOW(), NOW()),
('Hoodie', 'Comfortable cotton hoodie', 49.00, 'SKU-2002',
    (SELECT id FROM categories WHERE name='Fashion'), NOW(), NOW()),

('Blender Max', 'Kitchen blender 1200W', 129.00, 'SKU-3001',
    (SELECT id FROM categories WHERE name='Home & Kitchen'), NOW(), NOW()),
('Coffee Maker', 'Automatic coffee machine', 199.00, 'SKU-3002',
    (SELECT id FROM categories WHERE name='Home & Kitchen'), NOW(), NOW())
ON CONFLICT (sku) DO NOTHING;
