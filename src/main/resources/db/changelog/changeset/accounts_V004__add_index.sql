CREATE INDEX account_owner_idx ON account(owner_id);

ALTER TABLE owner
ADD CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id);

ALTER TABLE balance
ADD CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id);
