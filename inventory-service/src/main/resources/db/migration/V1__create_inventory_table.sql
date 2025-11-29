-- ============================
-- INVENTORY TABLE
-- ============================

CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(255) NOT NULL UNIQUE,
    total_quantity INTEGER NOT NULL,
    reserved_quantity INTEGER NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE inventory_reservations (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(255) NOT NULL,
    quantity_reserved INTEGER NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    reserved_at TIMESTAMP
);



