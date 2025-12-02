INSERT INTO inventory_projection (sku, total_quantity, reserved_quantity, available_quantity, updated_at)
VALUES
    ('SKU-1001', 10, 0, 10, NOW()),
    ('SKU-1002', 20, 0, 20, NOW()),
    ('SKU-1003', 30, 0, 30, NOW())
ON CONFLICT (sku) DO NOTHING;
