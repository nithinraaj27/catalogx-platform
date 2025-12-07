CREATE TABLE IF NOT EXISTS inventory_projection (
    sku VARCHAR(100) PRIMARY KEY,
    total_quantity INT NOT NULL DEFAULT 0,
    reserved_quantity INT NOT NULL DEFAULT 0,
    available_quantity INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP DEFAULT NOW()
);
