create TABLE free_account_numbers (
  id bigint primary key GENERATED ALWAYS AS IDENTITY unique;
  account_type VARCHAR(255) NOT NULL,unique
  account_number VARCHAR(20) NOT NULL,unique
);
create TABLE account_numbers_sequence (
  id bigint primary key GENERATED ALWAYS AS IDENTITY unique;
  account_type VARCHAR(255) NOT NULL,unique
  current_number BIGINT NOT NULL DEFAULT 0
);