-- Добавляем записи в таблицу owner
INSERT INTO owner (owner_type) VALUES ('USER');
INSERT INTO owner (owner_type) VALUES ('USER');
INSERT INTO owner (owner_type) VALUES ('PROJECT');
INSERT INTO owner (owner_type) VALUES ('PROJECT');
INSERT INTO owner (owner_type) VALUES ('USER');
INSERT INTO owner (owner_type) VALUES ('PROJECT');
INSERT INTO owner (owner_type) VALUES ('USER');

-- Предполагаем, что владельцы добавлены с id от 1 до 7, добавляем записи в таблицу account
INSERT INTO account (number, owner_id, account_type, currency, status) VALUES
    ('1234567890567890', 1, 'DEBIT', 'USD', 'ACTIVE'),
    ('1234567891567891', 2, 'CREDIT', 'EUR', 'ACTIVE'),
    ('1234567892678923', 3, 'SAVING', 'RUB', 'SUSPENDED'),
    ('1234567893567893', 4, 'CURRENT', 'USD', 'CLOSED'),
    ('1234567894789423', 5, 'INVESTMENT', 'EUR', 'BLOCKED'),
    ('1234567895567895', 6, 'OTHER', 'RUB', 'ACTIVE'),
    ('1234567896345678', 7, 'DEBIT', 'USD', 'ACTIVE');