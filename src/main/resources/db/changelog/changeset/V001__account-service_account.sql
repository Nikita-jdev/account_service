CREATE TABLE owner (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    owner_id BIGINT NOT NULL,
    owner_type smallint NOT NULL
);

CREATE TABLE account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    owner_id BIGINT,
    number VARCHAR(20) NOT NULL UNIQUE CHECK (number SIMILAR TO '[0-9]+' AND length(number) >= 12),
    account_type smallint NOT NULL,
    currency smallint NOT NULL,
    status smallint DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    closed_at TIMESTAMPTZ,
    version BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES owner (id)
);

CREATE UNIQUE INDEX account_idx ON account (number);