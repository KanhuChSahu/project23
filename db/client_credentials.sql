-- Create client_credentials table
CREATE TABLE IF NOT EXISTS client_credentials (
    client_id VARCHAR(255) PRIMARY KEY,
    client_secret VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true
);

-- Insert test client credentials
-- Test client 1 (active)
INSERT INTO client_credentials (client_id, client_secret, active)
VALUES ('test-client', '123e4567-e89b-42d3-a456-556642440000', true);

-- Test client 2 (inactive)
INSERT INTO client_credentials (client_id, client_secret, active)
VALUES ('inactive-client', '987fcdeb-51a2-41d3-af67-556642440001', false);

-- Note: The client_secret values are valid UUIDs
-- You should change these values in production environment