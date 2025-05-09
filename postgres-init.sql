-- Create the database and user
CREATE DATABASE droplet;
CREATE USER droplet_user WITH PASSWORD 'droplet_password';

-- Grant privileges on the new database
GRANT ALL PRIVILEGES ON DATABASE droplet TO droplet_user;

-- Connect to the new database to run further grants
\c droplet;

-- Give usage and create privileges on the public schema
GRANT USAGE, CREATE ON SCHEMA public TO droplet_user;

-- Set default privileges for future objects
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO droplet_user;



DROP SEQUENCE IF EXISTS seq_account_id;
DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id                    BIGINT       NOT NULL,
    name                  VARCHAR(255) NOT NULL,
    email                 VARCHAR(255) NOT NULL UNIQUE,
    password_hash         text         NOT NULL,
    created_at            TIMESTAMPTZ,
    used_storage_in_bytes BIGINT       NOT NULL,
    max_storage_in_bytes  BIGINT       NOT NULL,
    PRIMARY KEY (id)
);


CREATE SEQUENCE seq_account_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

GRANT ALL PRIVILEGES ON TABLE account TO droplet_user;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE seq_account_id TO droplet_user;
