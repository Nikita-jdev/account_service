CREATE TABLE account (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number bigint NOT NULL UNIQUE,
    owner_name VARCHAR(50)
);