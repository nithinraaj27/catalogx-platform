INSERT INTO carts (user_id)
SELECT 'userA'
WHERE NOT EXISTS (SELECT 1 FROM carts WHERE user_id = 'userA');

INSERT INTO carts (user_id)
SELECT 'userB'
WHERE NOT EXISTS (SELECT 1 FROM carts WHERE user_id = 'userB');

-- Items for userA
INSERT INTO cart_items (cart_id, sku, quantity, price)
SELECT c.id, 'SKU-1001', 2, 499.00
FROM carts c
WHERE c.user_id = 'userA'
AND NOT EXISTS (
    SELECT 1 FROM cart_items ci WHERE ci.cart_id = c.id AND ci.sku = 'SKU-1001'
);

INSERT INTO cart_items (cart_id, sku, quantity, price)
SELECT c.id, 'SKU-2002', 1, 1299.00
FROM carts c
WHERE c.user_id = 'userA'
AND NOT EXISTS (
    SELECT 1 FROM cart_items ci WHERE ci.cart_id = c.id AND ci.sku = 'SKU-2002'
);

-- Items for userB
INSERT INTO cart_items (cart_id, sku, quantity, price)
SELECT c.id, 'SKU-1002', 3, 899.00
FROM carts c
WHERE c.user_id = 'userB'
AND NOT EXISTS (
    SELECT 1 FROM cart_items ci WHERE ci.cart_id = c.id AND ci.sku = 'SKU-1002'
);

INSERT INTO cart_items (cart_id, sku, quantity, price)
SELECT c.id, 'SKU-3001', 1, 1999.00
FROM carts c
WHERE c.user_id = 'userB'
AND NOT EXISTS (
    SELECT 1 FROM cart_items ci WHERE ci.cart_id = c.id AND ci.sku = 'SKU-3001'
);

INSERT INTO cart_items (cart_id, sku, quantity, price)
SELECT c.id, 'SKU-1005', 1, 599.00
FROM carts c
WHERE c.user_id = 'userB'
AND NOT EXISTS (
    SELECT 1 FROM cart_items ci WHERE ci.cart_id = c.id AND ci.sku = 'SKU-1005'
);
