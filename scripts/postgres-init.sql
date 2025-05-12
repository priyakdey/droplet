-- create the database and user
CREATE DATABASE droplet;
CREATE USER droplet_user WITH PASSWORD 'droplet_password';

GRANT ALL PRIVILEGES ON DATABASE droplet TO droplet_user;

\c droplet;

GRANT USAGE, CREATE ON SCHEMA public TO droplet_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, DELETE, UPDATE ON TABLES TO droplet_user;

-- account table holds all account related information

DROP SEQUENCE IF EXISTS seq_account_id;
DROP INDEX IF EXISTS idx_account_email;
DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id                    INT          NOT NULL,
    email                 VARCHAR(254) NOT NULL,
    password_hash         TEXT         NOT NULL,
    is_enabled            BOOLEAN      NOT NULL DEFAULT false,
    used_storage_in_bytes INT          NOT NULL DEFAULT 0,
    max_storage_in_bytes  INT          NOT NULL DEFAULT 104857600,
    created_at            TIMESTAMPTZ  NOT NULL,
    updated_at            TIMESTAMPTZ  NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE seq_account_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE UNIQUE INDEX idx_account_email ON account (email);

-- profile table holds all account metadata

DROP SEQUENCE IF EXISTS seq_profile_id;
DROP TABLE IF EXISTS profile;


CREATE TABLE profile
(
    id             INT          NOT NULL,
    account_id     INT          NOT NULL,
    name           VARCHAR(255) NOT NULL,
    preferred_tz   TEXT         NOT NULL,
    container_name VARCHAR(255) NOT NULL,
    created_at     TIMESTAMPTZ  NOT NULL,
    updated_at     TIMESTAMPTZ  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES account
);

CREATE SEQUENCE seq_profile_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
