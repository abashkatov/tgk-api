CREATE TABLE tgk_users
(
    id       SERIAL       NOT NULL,
    username VARCHAR(180) NOT NULL,
    email    VARCHAR(180) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX UNIQ_2D3C432F85E0677 ON tgk_users (username);
CREATE UNIQUE INDEX UNIQ_2D3C432E7927C74 ON tgk_users (email);
