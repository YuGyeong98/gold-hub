CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type       VARCHAR(50)  NOT NULL,
    price      INT          NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number     VARCHAR(255) UNIQUE NOT NULL,
    customer_id      VARCHAR(255)        NOT NULL,
    status           VARCHAR(50)         NOT NULL,
    type             VARCHAR(50)         NOT NULL,
    quantity         DECIMAL(10, 2)      NOT NULL,
    price            INT                 NOT NULL,
    delivery_address VARCHAR(255)        NOT NULL,
    created_at       TIMESTAMP(6)        NOT NULL,
    updated_at       TIMESTAMP(6)        NOT NULL,
    product_id       BIGINT              NOT NULL,
    CONSTRAINT fk_product_orders FOREIGN KEY (product_id) REFERENCES product (product_id)
);

INSERT INTO product
VALUES (1, 'GOLD_99_9', 100000, NOW(), NOW());

INSERT INTO product
VALUES (2, 'GOLD_99_99', 200000, NOW(), NOW());