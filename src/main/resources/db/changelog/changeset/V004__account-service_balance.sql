CREATE TABLE IF NOT EXISTS balance (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_number VARCHAR(20) NOT NULL,
    authorization_balance NUMERIC NOT NULL,
    clearing_balance NUMERIC NOT NULL,
    created_at TIMESTAMPTZ DEFAULT current_timestamp,
    updated_at TIMESTAMPTZ DEFAULT current_timestamp,
    version BIGINT DEFAULT 1 NOT NULL,

    CONSTRAINT fk_account_number FOREIGN KEY (account_number) REFERENCES account(number)
);