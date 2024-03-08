CREATE TABLE balances (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number bigint NOT NULL,
    authorization_balance bigint NOT NULL,
    actual_balance bigint NOT NULL,
    created_at timestamptz DEFAULT current_timestamp,
    change_at timestamptz DEFAULT current_timestamp,
    version bigint NOT NULL
);