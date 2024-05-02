CREATE TABLE IF NOT EXISTS clients (
    id SERIAL NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    discount_points BIGINT NOT NULL,
    PRIMARY KEY(card_number)
);

CREATE TABLE IF NOT EXISTS order_checks (
    id SERIAL NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    amount DECIMAL NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_card_number
        FOREIGN KEY(card_number)
            REFERENCES clients(card_number)
);

CREATE TABLE IF NOT EXISTS check_positions (
    id SERIAL NOT NULL,
    check_id BIGINT,
    pos_amount DECIMAL NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_id
        FOREIGN KEY(check_id)
            REFERENCES order_checks(id)
);