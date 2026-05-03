CREATE TABLE IF NOT EXISTS users (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100)  NOT NULL,
    email      VARCHAR(150)  NOT NULL UNIQUE,
    role       VARCHAR(20)   NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_user_email ON users(email);
