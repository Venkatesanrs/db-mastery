CREATE TABLE IF NOT EXISTS departments (
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS employees (
    id            BIGSERIAL PRIMARY KEY,
    department_id BIGINT       REFERENCES departments(id) ON DELETE SET NULL,
    name          VARCHAR(100) NOT NULL,
    title         VARCHAR(100)
);

CREATE INDEX idx_employees_department_id ON employees(department_id);
