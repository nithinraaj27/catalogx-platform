INSERT INTO orders (sku, quantity, status, created_at, updated_at)
VALUES
    ('SKU-1001', 2, 'PENDING', NOW(), NOW()),
    ('SKU-2001', 1, 'PENDING', NOW(), NOW()),
    ('SKU-3001', 5, 'REJECTED', NOW(), NOW());
