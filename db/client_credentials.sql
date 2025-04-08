-- Create client_credentials table
CREATE TABLE IF NOT EXISTS client_credentials (
    client_id VARCHAR(255) PRIMARY KEY,
    client_secret VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true
);

-- Insert test client credentials
-- Test client 1 (active)
INSERT INTO client_credentials (client_id, client_secret, active)
VALUES ('test-client', '$2a$10$8KxX1P1QYeYZZ5TZ5XZzN.YG4KjLxh4vyZkH7P3w4T/E0Qm4LGnPS', true);

-- Test client 2 (inactive)
INSERT INTO client_credentials (client_id, client_secret, active)
VALUES ('inactive-client', '$2a$10$8KxX1P1QYeYZZ5TZ5XZzN.YG4KjLxh4vyZkH7P3w4T/E0Qm4LGnPS', false);

-- Note: The client_secret values are BCrypt hashed. The original value is 'secret123'
-- You should change these values in production environment