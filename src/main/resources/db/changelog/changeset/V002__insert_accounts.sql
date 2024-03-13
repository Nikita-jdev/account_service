INSERT INTO owner (owner_id, owner_type) VALUES (1, 'USER'), (20, 'PROJECT');


INSERT INTO account (owner_id, number, account_type,
                     currency, status, created_at, updated_at,
                     closed_at, version) VALUES
(1, '2020303040405050', 'CREDIT', 'RUB', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
(2, '1010101010101010', 'PAYMENT', 'RUB', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1),
(1, '9999999999999999', 'DEPOSIT', 'CNY', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 1);
