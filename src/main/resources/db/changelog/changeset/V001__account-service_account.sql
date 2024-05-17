CREATE TABLE owner
(
    id       BIGSERIAL PRIMARY KEY UNIQUE,
    owner_id BIGINT     NOT NULL UNIQUE,
    type     VARCHAR(8) NOT NULL
);

CREATE TABLE account
(
    id     BIGSERIAL PRIMARY KEY UNIQUE,
    number VARCHAR(20) NOT NULL UNIQUE CHECK (LENGTH(number) >= 12 AND number SIMILAR TO '[0-9]+'),
    owner_id       BIGINT NOT NULL,
    type           SMALLINT NOT NULL,
    currency       SMALLINT NOT NULL,
    status         SMALLINT NOT NULL DEFAULT 0,
    status_details VARCHAR(128),
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    closed_at      TIMESTAMPTZ,
    version        BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_account_owner FOREIGN KEY (owner_id) REFERENCES owner (id)
);

CREATE INDEX index_number_payment ON account (number);
CREATE INDEX index_owner_id ON account (owner_id);

CREATE TABLE balance
(
    id             BIGSERIAL PRIMARY KEY UNIQUE,
    account_id     BIGINT     NOT NULL,
    auth_balance   BIGINT,
    actual_balance BIGINT,
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    last_update_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    version        BIGINT     NOT NULL,
    currency       VARCHAR(4) NOT NULL,

    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id)
);
