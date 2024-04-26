CREATE TABLE free_account_numbers (
  id SERIAL PRIMARY KEY,
  account_type VARCHAR(255) NOT NULL,
  account_number VARCHAR(20) NOT NULL,
  unique (account_type, account_number)
);