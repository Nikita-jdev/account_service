CREATE TABLE IF NOT EXISTS request
(
    token          UUID PRIMARY KEY DEFAULT,
    user_id        BIGINT NOT NULL UNIQUE,
    request_type   varchar(60) NOT NULL,
    lock_value     BIGINT NOT NULL,
    is_open        boolean default true,
    request_data   jsonb NOT NULL,
    request_status varchar(60) NOT NULL,
    addit_status   varchar(130),
    created_at     timestamptz DEFAULT current_timestamp,
    updated_at     timestamptz DEFAULT current_timestamp,
    version        smallint default 1
);
