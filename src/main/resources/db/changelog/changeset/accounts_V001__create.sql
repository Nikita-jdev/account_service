CREATE TABLE account (
    id              BIGINT       PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number          VARCHAR(20)  NOT NULL CHECK (LENGTH(number) BETWEEN 12 AND 20),
    owner_id        BIGINT       NOT NULL,
    account_type    VARCHAR(255) NOT NULL,
    currency        VARCHAR(255) NOT NULL,
    account_status  VARCHAR(255) NOT NULL,
    created_at      TIMESTAMPTZ  DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ  DEFAULT CURRENT_TIMESTAMP,
    closed_at       TIMESTAMPTZ,
    version         BIGINT       NOT NULL DEFAULT 1
);
