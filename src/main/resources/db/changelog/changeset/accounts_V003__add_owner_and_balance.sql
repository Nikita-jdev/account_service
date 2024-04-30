CREATE TABLE owner (
    id              BIGINT      PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id      BIGINT      NOT NULL,
    owner_type      VARCHAR(24) NOT NULL;
)

ALTER TABLE account (
    ADD COLUMN balance_id   BIGINT  NOT NULL;
)
