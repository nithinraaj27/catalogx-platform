INSERT INTO inventory_projection (sku, total_quantity, reserved_quantity, available_quantity, updated_at)
VALUES
    ('SKU-1001', 50, 0, 50, NOW()),
    ('SKU-1002', 100, 0, 100, NOW()),
    ('SKU-1003', 75, 0, 75, NOW()),
    ('SKU-1004', 20, 0, 20, NOW()),
    ('SKU-1005', 10, 0, 10, NOW())
ON CONFLICT (sku) DO NOTHING;
