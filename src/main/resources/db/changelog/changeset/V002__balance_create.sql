create TABLE balance (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number bigint NOT NULL,
    authorization_balance DECIMAL(19, 2) NOT NULL,
    actual_balance DECIMAL(19, 2) NOT NULL,
    created_at timestamptz DEFAULT current_timestamp,
    change_at timestamptz DEFAULT current_timestamp,
    version bigint NOT null,
    CONSTRAINT fk_account_number
    FOREIGN KEY (account_number)
    REFERENCES account (id)
);