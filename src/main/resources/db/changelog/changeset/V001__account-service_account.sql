CREATE EXTENSION "uuid-ossp";

CREATE TABLE request
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id          BIGINT   NOT NULL,
    operation_type   SMALLINT NOT NULL,
    lock_value       BIGINT   NOT NULL,
    is_open          BOOLEAN  NOT NULL,
    input_data       JSONB    NOT NULL,
    operation_status SMALLINT NOT NULL,
    status_details   VARCHAR(255),
    created_at       TIMESTAMPTZ      DEFAULT current_timestamp,
    updated_at       TIMESTAMPTZ      DEFAULT current_timestamp,
    version          BIGINT           DEFAULT 1

    /*CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT unique_lock UNIQUE (lock_value)*/
);