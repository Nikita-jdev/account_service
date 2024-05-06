CREATE TABLE free_account_numbers (
  type VARCHAR(32) NOT NULL,
  account_number BIGINT NOT NULL,
  CONSTRAINT free_pr_key PRIMARY KEY (type, account_number),
  FOREIGN KEY (type) REFERENCES account_numbers_sequence(type)
);
CREATE TABLE account_numbers_sequence (
  type VARCHAR(32) NOT NULL PRIMARY KEY,
  counter BIGINT NOT NULL DEFAULT 1
);