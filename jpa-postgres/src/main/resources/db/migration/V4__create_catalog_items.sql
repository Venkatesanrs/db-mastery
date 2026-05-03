CREATE TABLE IF NOT EXISTS catalog_items (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150)   NOT NULL,
    category    VARCHAR(100),
    price       NUMERIC(12, 2) NOT NULL,
    description TEXT
);
