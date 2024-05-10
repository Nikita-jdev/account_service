CREATE TABLE free_account_numbers (
    account_number BIGINT NOT NULL,
    type VARCHAR(32) NOT NULL,
    CONSTRAINT free_ac_pk PRIMARY KEY (account_number, type)
);
CREATE TABLE account_numbers_sequence (
    type VARCHAR(32) NOT NULL PRIMARY KEY,
    counter BIGINT NOT NULL DEFAULT 1
)