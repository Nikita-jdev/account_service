CREATE TABLE free_account_numbers (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  account_type VARCHAR(32) NOT NULL,
  account_number BIGING(12) NOT NULL,
  CONSTRAINT primary_key PRIMARY KEY (account_type, account_number)
);

CREATE TABLE account_numbers_sequence (
  account_type VARCHAR(32) PRIMARY KEY,
  current_number BIGINT NOT NULL DEFAULT 0
);