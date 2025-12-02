CREATE TABLE if not exists inventory_projection (
    sku VARCHAR(255) PRIMARY KEY,
    total_quantity INTEGER,
    reserved_quantity INTEGER,
    available_quantity INTEGER,
    updated_at TIMESTAMP
);
