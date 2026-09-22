MERGE INTO users (id, name, email, password_hash, role)
KEY(email) VALUES (1, 'KaviMart Admin', 'admin@kavimart.local', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');
MERGE INTO users (id, name, email, password_hash, role)
KEY(email) VALUES (2, 'Demo Seller', 'seller@kavimart.local', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'SELLER');
MERGE INTO users (id, name, email, password_hash, role)
KEY(email) VALUES (3, 'Demo Buyer', 'buyer@kavimart.local', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'BUYER');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (1, 2, 'Handcrafted Notebook', 'A durable notebook for ideas, plans, and sketches.', 299.00, 25, 'Stationery', 'https://images.unsplash.com/photo-1517842645767-c639042777db?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (2, 2, 'Ceramic Coffee Mug', 'A minimal ceramic mug made for the morning ritual.', 449.00, 12, 'Home', 'https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?w=800');
-- Explicit demo ids keep foreign keys readable; move identity sequences past them.
ALTER TABLE users ALTER COLUMN id RESTART WITH 1000;
ALTER TABLE products ALTER COLUMN id RESTART WITH 1000;