CREATE TABLE IF NOT EXISTS account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number VARCHAR(20) NOT NULL UNIQUE CHECK (LENGTH(number) BETWEEN 12 AND 20),
    owner_id BIGINT NOT NULL,
    owner_type VARCHAR(255) NOT NULL,
    account_type VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    account_status  VARCHAR(255) NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP,
    closed_at timestamptz,
    version BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_owner_id ON account (owner_id);

