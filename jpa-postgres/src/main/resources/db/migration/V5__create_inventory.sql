CREATE TABLE IF NOT EXISTS inventory (
    id           BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(150)   NOT NULL,
    stock_count  INTEGER        NOT NULL DEFAULT 0,
    price        NUMERIC(12, 2) NOT NULL,
    version      BIGINT         NOT NULL DEFAULT 0   -- managed by Hibernate @Version
);
