CREATE TABLE accounts (
    id              BIGINT       PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number          VARCHAR(20)  NOT NULL CHECK (LENGTH(number) BETWEEN 12 AND 20),
    owner_id        BIGINT,
    project_id      BIGINT,
    account_type    VARCHAR(255) NOT NULL,
    currency        VARCHAR(255) NOT NULL,
    account_status  VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    closed_at       TIMESTAMP,
    version         BIGINT
);
