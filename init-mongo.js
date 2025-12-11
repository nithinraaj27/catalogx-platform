db = db.getSiblingDB('searchdb');

db.createUser({
  user: "searchuser",
  pwd: "searchpass",
  roles: [
    { role: "readWrite", db: "searchdb" }
  ]
});

db.createCollection("product_projection");
db.createCollection("inventory_projection");
db.createCollection("sku_product_mapping");
