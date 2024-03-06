CREATE TABLE free_account_numbers (
    number VARCHAR(20) NOT NULL UNIQUE CHECK (number SIMILAR TO '[0-9]+' AND length(number) >= 12),
    account_type VARCHAR(20) NOT NULL,

    CONSTRAINT free_account_pk UNIQUE (account_type, number)
);

CREATE TABLE free_account_numbers_sequence (
    account_type VARCHAR(20) NOT NULL,
    count BIGINT NOT NULL DEFAULT 0
);