-- ============================================================
--  INVENTORY SERVICE SYNC DATA (COMBINED SEED FILE)
--  Contains:
--    1. Initial stock for all 7 SKUs
--    2. Reservation history for SKUs that have reservations
-- ============================================================

------------------------------------------------------------
-- 1. INSERT INITIAL INVENTORY DATA (SYNCED ACROSS SERVICES)
------------------------------------------------------------
INSERT INTO inventory (sku, total_quantity, reserved_quantity, updated_at)
VALUES
    ('SKU-1001', 100, 0, NOW()),
    ('SKU-1002', 50, 10, NOW()),
    ('SKU-1003', 200, 20, NOW()),
    ('SKU-2001', 40, 0, NOW()),
    ('SKU-2002', 70, 0, NOW()),
    ('SKU-3001', 30, 0, NOW()),
    ('SKU-3002', 50, 0, NOW())
ON CONFLICT (sku) DO NOTHING;

------------------------------------------------------------
-- 2. INSERT RESERVATION RECORDS
-- (Only for SKUs that actually have reserved quantities)
------------------------------------------------------------
INSERT INTO inventory_reservations (sku, quantity_reserved, order_id, reserved_at)
VALUES
    ('SKU-1002', 5, 'ORDER-001', NOW()),
    ('SKU-1003', 10, 'ORDER-002', NOW())
ON CONFLICT DO NOTHING;
