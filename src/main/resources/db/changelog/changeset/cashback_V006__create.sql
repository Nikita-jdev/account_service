CREATE TABLE cashback_tariff (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cashback_tariff_name ON cashback_tariff(title);
CREATE INDEX idx_cashback_tariff_created_at ON cashback_tariff(created_at);

CREATE TABLE cashback_operation_mapping (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cashback_tariff_id BIGINT NOT NULL,
    operation_type VARCHAR(255) NOT NULL,
    cashback_percentage DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_cashback_operation_mapping_cashback_tariff FOREIGN KEY (cashback_tariff_id) REFERENCES cashback_tariff(id)
);

CREATE INDEX idx_cashback_operation_mapping_cashback_tariff_id ON cashback_operation_mapping(cashback_tariff_id);
CREATE INDEX idx_cashback_operation_mapping_operation_type ON cashback_operation_mapping(operation_type);
CREATE INDEX idx_cashback_operation_mapping_composite ON cashback_operation_mapping(cashback_tariff_id, operation_type);

CREATE TABLE merchant_mapping (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cashback_tariff_id BIGINT       NOT NULL,
    merchant_id VARCHAR(255) NOT NULL,
    cashback_percentage DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_merchant_mapping_cashback_tariff FOREIGN KEY (cashback_tariff_id) REFERENCES cashback_tariff(id)
);

CREATE INDEX idx_merchant_mapping_cashback_tariff_id ON merchant_mapping(cashback_tariff_id);
CREATE INDEX idx_merchant_mapping_merchant_id ON merchant_mapping(merchant_id);
CREATE INDEX idx_merchant_mapping_composite ON merchant_mapping(cashback_tariff_id, merchant_id);

ALTER TABLE account
ADD cashback_tariff VARCHAR(32) NOT NULL;
