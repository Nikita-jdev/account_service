CREATE TABLE free_account_numbers (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number VARCHAR(20) NOT NULL UNIQUE CHECK (number SIMILAR TO '[0-9]+' AND length(number) >= 12),
    account_type VARCHAR(20) NOT NULL,

    CONSTRAINT uc_number_account_type UNIQUE (account_type, number)
);

CREATE TABLE free_account_numbers_sequence (
    account_type VARCHAR(20) NOT NULL,
    count BIGINT NOT NULL DEFAULT 1
);

INSERT INTO free_account_numbers_sequence (account_type, count) VALUES
('payment', 0),
('credit', 0),
('investment', 0),
('currency', 0),
('deposit', 0),
('special', 0);
