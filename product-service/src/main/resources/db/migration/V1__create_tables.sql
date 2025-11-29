-- ============================
-- CATEGORY TABLE
-- ============================

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- ============================
-- PRODUCT TABLE
-- ============================

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(19,2) NOT NULL,
    sku VARCHAR(255) NOT NULL UNIQUE,
    category_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- ============================
-- PRODUCT ATTRIBUTES TABLE
-- ============================

CREATE TABLE product_attributes (
    id BIGSERIAL PRIMARY KEY,
    attribute_key VARCHAR(255) NOT NULL,
    attribute_value VARCHAR(255) NOT NULL,
    product_id BIGINT,
    CONSTRAINT fk_attribute_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

