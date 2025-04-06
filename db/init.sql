-- Create database if not exists
CREATE DATABASE IF NOT EXISTS fair_rummy;

-- Connect to fair_rummy database
\c fair_rummy;

-- Create player table
CREATE TABLE IF NOT EXISTS player (
    player_id SERIAL PRIMARY KEY,
    mobile_number VARCHAR(15) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    profile_picture TEXT,
    pan_verified BOOLEAN DEFAULT FALSE,
    bank_verified BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_timestamp TIMESTAMP
);

-- Create index on mobile_number for faster lookups
CREATE INDEX IF NOT EXISTS idx_player_mobile_number ON player(mobile_number);

-- Create OTP table
CREATE TABLE IF NOT EXISTS otp (
    otp_id SERIAL PRIMARY KEY,
    mobile_number VARCHAR(15) NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    verified BOOLEAN DEFAULT FALSE
);

-- Create index on mobile_number and expires_at for OTP cleanup
CREATE INDEX IF NOT EXISTS idx_otp_mobile_number ON otp(mobile_number);
CREATE INDEX IF NOT EXISTS idx_otp_expires_at ON otp(expires_at);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_player_updated_at
    BEFORE UPDATE ON player
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();