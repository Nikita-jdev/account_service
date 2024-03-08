CREATE TABLE balances (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number bigint NOT NULL,
    authorization_balance DECIMAL(19, 2),
    actual_balance DECIMAL(19, 2),
    created_at timestamptz DEFAULT current_timestamp,
    change_at timestamptz DEFAULT current_timestamp,
    version bigint NOT NULL
);

CREATE TABLE accounts (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number bigint NOT NULL
    owner_name VARCHAR(255),
    balance_id bigint,
    CONSTRAINT fk_balance
    FOREIGN KEY (balance_id)
    REFERENCES balances (id)
    ON DELETE CASCADE
);