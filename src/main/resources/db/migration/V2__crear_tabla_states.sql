CREATE TABLE states (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    country_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    erased BOOLEAN NOT NULL,
    CONSTRAINT fk_states_country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE
);