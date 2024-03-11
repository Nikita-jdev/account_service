CREATE TABLE IF NOT EXISTS owner(
    id BIGINT PRIMARY KEY GENERATED BY ALWAYS AS IDENTITY,
    owner_type VARCHAR(16) NOT NULL CHECK (owner_type IN ('USER', 'PROJECT'))
    );

CREATE TABLE IF NOT EXISTS account(
    id BIGINT PRIMARY KEY GENERATED BY ALWAYS AS IDENTITY,
    number VARCHAR (20) NOT NULL,
    owner_id BIGINT NOT NULL,
    account_type VARCHAR(16) NOT NULL CHECK (account_type IN ('DEBET, CREDIT, SAVING, CURRENT, INVESTMENT, OTHER')),
    currency VARCHAR(4) NOT NULL CHECK (currency IN ('USD', 'EUR', 'RUB')),
    status VARCHAR(16) NOT NULL CHECK (status IN ('ACTIVE', 'FROZEN', 'CLOSED')),
    created_at TIMESTAMPTZ DEFAULT current_timestamp,
    updated_at TIMESTAMPTZ DEFAULT current_timestamp,
    closed_at TIMESTAMPTZ,
    version BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES owner(id)
    );

    CREATE UNIQUE INDEX account_owner_idx ON account(owner_id);