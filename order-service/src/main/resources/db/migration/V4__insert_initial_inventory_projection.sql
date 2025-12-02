INSERT INTO inventory_projection (sku, total_quantity, reserved_quantity, available_quantity, updated_at)
VALUES
    ('SKU-1001', 100, 0, 100, NOW()),
    ('SKU-1002', 50, 10, 40, NOW()),
    ('SKU-1003', 200, 20, 180, NOW()),
    ('SKU-2001', 40, 0, 40, NOW()),
    ('SKU-2002', 70, 0, 70, NOW()),
    ('SKU-3001', 30, 0, 30, NOW()),
    ('SKU-3002', 50, 0, 50, NOW())
ON CONFLICT (sku) DO NOTHING;
