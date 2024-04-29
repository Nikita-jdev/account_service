create TABLE free_account_numbers (
  id bigint primary key GENERATED ALWAYS AS IDENTITY unique;
  account_type VARCHAR(255) NOT NULL,unique
  account_number VARCHAR(20) NOT NULL,unique
);
CREATE TABLE account_numbers_sequence (
  account_type VARCHAR(255) PRIMARY KEY,
  current_number BIGINT NOT NULL DEFAULT 0
);