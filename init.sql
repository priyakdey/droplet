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
