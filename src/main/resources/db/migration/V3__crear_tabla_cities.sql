CREATE TABLE cities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    state_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    erased BOOLEAN NOT NULL,
    CONSTRAINT fk_cities_state FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);