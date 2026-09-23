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
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (3, 2, 'Wireless Earbuds', 'Compact earbuds with clear sound and long battery life.', 1299.00, 30, 'Electronics', 'https://images.unsplash.com/photo-1572569511254-d8f925fe2cbb?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (4, 2, 'Cotton Tote Bag', 'Eco-friendly tote bag for everyday shopping and errands.', 199.00, 50, 'Accessories', 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (5, 2, 'Scented Candle Set', 'Set of 3 soy wax candles in calming lavender, rose, and vanilla.', 599.00, 20, 'Home', 'https://images.unsplash.com/photo-1602178506148-9cfddbe4bad3?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (6, 2, 'Stainless Steel Water Bottle', '750ml insulated bottle that keeps drinks cold for 24 hours.', 799.00, 40, 'Kitchen', 'https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (7, 2, 'Desk Plant Pot', 'Small ceramic pot perfect for succulents on your work desk.', 349.00, 18, 'Home', 'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (8, 2, 'Leather Wallet', 'Slim genuine leather wallet with 6 card slots and a bill pocket.', 899.00, 15, 'Accessories', 'https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (9, 2, 'Wooden Pen Set', 'Pack of 3 handcrafted wooden ballpoint pens — great gift idea.', 399.00, 35, 'Stationery', 'https://images.unsplash.com/photo-1583485088034-697b5bc54ccd?w=800');
MERGE INTO products (id, seller_id, name, description, price, stock_qty, category, image_url)
KEY(id) VALUES (10, 2, 'Yoga Mat', 'Non-slip 6mm thick yoga mat with carrying strap, 183cm x 61cm.', 1099.00, 22, 'Sports', 'https://images.unsplash.com/photo-1601925228114-87a8e0b87acd?w=800');

ALTER TABLE users ALTER COLUMN id RESTART WITH 1000;
ALTER TABLE products ALTER COLUMN id RESTART WITH 1000;