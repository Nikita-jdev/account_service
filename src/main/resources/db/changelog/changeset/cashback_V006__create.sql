CREATE TABLE cashback_tariff (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX (title),
    INDEX (created_at)
);

CREATE TABLE cashback_operation_mapping (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cashback_tariff_id BIGINT NOT NULL,
    operation_type VARCHAR(255) NOT NULL,
    cashback_percentage DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_cashback_operation_mapping_cashback_tariff FOREIGN KEY (cashback_tariff_id) REFERENCES cashback_tariff(id),

     INDEX (cashback_tariff_id),
     INDEX (operation_type),
     INDEX (cashback_tariff_id, operation_type)
);

CREATE TABLE merchant_mapping (
    id              BIGINT       PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cashback_tariff_id BIGINT       NOT NULL,
    merchant_id      VARCHAR(255) NOT NULL,
    cashback_percentage DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_merchant_mapping_cashback_tariff FOREIGN KEY (cashback_tariff_id) REFERENCES cashback_tariff(id),

    INDEX (cashback_tariff_id),
    INDEX (merchant_id),
    INDEX (cashback_tariff_id, merchant_id)
);