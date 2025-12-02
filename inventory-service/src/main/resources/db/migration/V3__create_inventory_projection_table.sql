CREATE TABLE IF NOT EXISTS inventory_projection (
    sku VARCHAR(255) PRIMARY KEY,
    total_quantity INTEGER NOT NULL,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    available_quantity INTEGER NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW()
);
