DROP SEQUENCE IF EXISTS seq_account_id;
DROP INDEX IF EXISTS idx_account_email;
DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id            BIGINT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash text         NOT NULL,
    created_at    TIMESTAMPTZ,
    PRIMARY KEY (id)
);

CREATE INDEX idx_account_email ON account (email);

CREATE SEQUENCE seq_account_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

