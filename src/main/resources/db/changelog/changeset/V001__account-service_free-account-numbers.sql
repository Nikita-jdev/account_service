CREATE TABLE free_account_numbers (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  account_type VARCHAR(255) NOT NULL,
  account_number VARCHAR(20) NOT NULL,
  UNIQUE (account_type, account_number)
);

CREATE TABLE account_numbers_sequence (
  account_type VARCHAR(255) PRIMARY KEY,
  current_number BIGINT NOT NULL DEFAULT 0
);