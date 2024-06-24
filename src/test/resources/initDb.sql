CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    discount_points BIGINT NOT NULL
);

CREATE TABLE order_checks (
    id SERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE check_positions (
    id SERIAL PRIMARY KEY,
    check_id BIGINT NOT NULL,
    pos_amount BIGINT NOT NULL,
    FOREIGN KEY (check_id) REFERENCES order_checks(id)
);

INSERT INTO clients (card_number, discount_points) VALUES
('11112222111122221111', 25),
--('55556666555566665555', 107),
--('12341234123412341234', 211),
--('22222233333377777755', 74),
--('75315985245645682591', 113),
--('98769876987698769876', 90),
('11115555999977773333', 447);

INSERT INTO order_checks (client_id, amount) VALUES
(1, 7017),
(2, 293),
(2, 2807);

INSERT INTO check_positions (check_id, pos_amount) VALUES
(1, 550),
(1, 390),
(1, 1075),
(1, 2320),
(2, 990),
(2, 1850),
(3, 3700);
--(4, 1740),
--(4, 2175),
--(5, 3980),
--(5, 5075),
--(6, 7730),
--(6, 8740),
--(7, 3090),
--(7, 2150),
--(7, 1770),
--(8, 4220),
--(8, 3100),
--(8, 6350),
--(8, 2290);