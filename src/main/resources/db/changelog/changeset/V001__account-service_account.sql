CREATE TABLE IF NOT EXISTS request
(
    id             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    token          UUID  NOT NULL UNIQUE,
    owner_id       BIGINT NOT NULL,
    owner_type     VARCHAR(7) NOT NULL,
    request_type   VARCHAR(64) NOT NULL,
    lock_value     BIGINT NOT NULL,
    is_open        BOOLEAN default true,
    request_data   TEXT NOT NULL,
    request_status VARCHAR(64) NOT NULL,
    status_details VARCHAR(1024),
    created_at     timestamptz,
    updated_at     timestamptz,
    version        BIGINT NOT NULL

    CREATE INDEX request_owner_id ON request (owner_id, owner_type);
    CREATE UNIQUE INDEX block_open_request ON request (is_open) WHERE is_open = true;
);
