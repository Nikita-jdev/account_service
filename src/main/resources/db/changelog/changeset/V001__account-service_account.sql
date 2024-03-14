CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE request (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id BIGINT NOT NULL,
    request_type VARCHAR(64) NOT NULL,
    lock_value INT NOT NULL,
    is_open_request BOOLEAN NOT NULL,
    input_data JSONB NOT NULL,
    request_status VARCHAR(64) NOT NULL,
    status_details VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version INT DEFAULT 1
);

