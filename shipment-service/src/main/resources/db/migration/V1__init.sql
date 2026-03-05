CREATE TABLE IF NOT EXISTS shipments (
                                         id UUID PRIMARY KEY,
                                         tracking_number VARCHAR(64) NOT NULL UNIQUE,
    sender_name VARCHAR(120) NOT NULL,
    receiver_name VARCHAR(120) NOT NULL,
    origin_city VARCHAR(80) NOT NULL,
    destination_city VARCHAR(80) NOT NULL,
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMP NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_shipments_status ON shipments(status);