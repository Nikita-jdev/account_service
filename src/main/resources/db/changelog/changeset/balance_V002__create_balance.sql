CREATE TABLE balance (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_id bigint NOT NULL,
    authorization_balance decimal NOT NULL,
    actual_balance decimal NOT NULL,
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp,
    version bigint NOT NULL DEFAULT 1,

    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
)