CREATE TABLE free_account_numbers
(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number VARCHAR(20) UNIQUE CHECK (number SIMILAR TO '[0-9]+' AND length(number) >= 12) NOT NULL,
    account_type VARCHAR(20) NOT NULL
);

CREATE TABLE free_account_numbers_sequence
(
    account_type VARCHAR(20) NOT NULL,
    count BIGINT NOT NULL DEFAULT 0
);

INSERT INTO free_account_numbers_sequence (account_type, count)
VALUES ('PAYMENT', 0),
       ('CREDIT', 0),
       ('INVESTMENT', 0),
       ('CURRENCY', 0),
       ('DEPOSIT', 0),
       ('SPECIAL', 0);