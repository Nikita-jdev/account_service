ALTER TABLE request DROP COLUMN lock_value;
ALTER TABLE request ADD COLUMN lock_value VARCHAR(255);