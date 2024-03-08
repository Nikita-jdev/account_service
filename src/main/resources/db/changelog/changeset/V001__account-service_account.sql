CREATE TABLE request
(
    token            uuid PRIMARY KEY UNIQUE NOT NULL,
    user_id          bigint                  NOT NULL,
    operation_type   smallint                NOT NULL,
    lock_value       bigint                  NOT NULL,
    is_open          boolean                 NOT NULL,
    input_data       jsonb                   NOT NULL,
    operation_status smallint                NOT NULL,
    status_details   varchar(255),
    created_at       timestamptz DEFAULT current_timestamp,
    updated_at       timestamptz DEFAULT current_timestamp,
    version          bigint      DEFAULT 1

    /*CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT unique_lock UNIQUE (lock)*/
);