-- Write your sql migration here!
CREATE TABLE owner (
id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
owner_id bigint UNIQUE NOT NULL,
owner_type smallint NOT NULL,
created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
updated_at timestamptz DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE account (
id bigint PRIMARY KEY generated always AS IDENTITY UNIQUE,
account_number varchar (20) NOT NULL UNIQUE,
account_owner bigint REFERENCES owner(id),
account_type varchar (32),
currency varchar (3) NOT NULL,
status varchar (16),
created_at timestamptz DEFAULT CURRENT_TIMESTAMP,
updated_at timestamptz DEFAULT CURRENT_TIMESTAMP,
closed_at timestamptz DEFAULT CURRENT_TIMESTAMP,
version bigint
)