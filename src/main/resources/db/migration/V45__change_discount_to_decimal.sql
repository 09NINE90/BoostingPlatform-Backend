ALTER TABLE customer_profile
    ALTER COLUMN discount_percentage TYPE DECIMAL(5, 2) USING discount_percentage::DECIMAL;

ALTER TABLE customer_profile
    ALTER COLUMN discount_percentage SET DEFAULT 0.01;

UPDATE customer_profile
SET discount_percentage = 0.01
WHERE discount_percentage = 1;

UPDATE customer_profile
SET discount_percentage = 0.05
WHERE discount_percentage = 5;

UPDATE customer_profile
SET discount_percentage = 0.1
WHERE discount_percentage = 10;
