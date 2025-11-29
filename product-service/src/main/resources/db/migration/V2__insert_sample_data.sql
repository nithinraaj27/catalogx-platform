-- Insert Categories
INSERT INTO categories (name, description)
VALUES
    ('Electronics', 'Electronic gadgets and devices'),
    ('Fashion', 'Clothes and accessories')
ON CONFLICT (name) DO NOTHING;


-- Insert Products
INSERT INTO products (name, description, price, sku, category_id, created_at, updated_at)
VALUES
    (
        'Iphone 17',
        'A great phone',
        699.99,
        'SKU-IP-17',
        (SELECT id FROM categories WHERE name = 'Electronics'),
        now(),
        now()
    ),
    (
        'T-Shirt',
        'Comfortable cotton',
        19.99,
        'SKU-TSHIRT-001',
        (SELECT id FROM categories WHERE name = 'Fashion'),
        now(),
        now()
    )
ON CONFLICT (sku) DO NOTHING;


-- Insert Product Attributes
INSERT INTO product_attributes (attribute_key, attribute_value, product_id)
VALUES
    ('color', 'black',  (SELECT id FROM products WHERE sku = 'SKU-SMART-001')),
    ('storage', '256GB', (SELECT id FROM products WHERE sku = 'SKU-SMART-001')),
    ('size', 'L',        (SELECT id FROM products WHERE sku = 'SKU-TSHIRT-001'))
ON CONFLICT DO NOTHING;
