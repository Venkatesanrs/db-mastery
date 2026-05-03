CREATE TABLE IF NOT EXISTS orders (
    id              BIGSERIAL PRIMARY KEY,
    customer_name   VARCHAR(100)   NOT NULL,
    order_date      TIMESTAMP      NOT NULL DEFAULT NOW(),
    total_amount    NUMERIC(12, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
    id           BIGSERIAL PRIMARY KEY,
    order_id     BIGINT         NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_name VARCHAR(100)   NOT NULL,
    quantity     INTEGER        NOT NULL,
    unit_price   NUMERIC(12, 2) NOT NULL
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
