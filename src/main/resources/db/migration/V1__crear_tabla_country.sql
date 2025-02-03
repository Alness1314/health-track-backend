CREATE TABLE country (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    code VARCHAR(64),
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    erased BOOLEAN NOT NULL
);

CREATE TABLE states (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    country_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    erased BOOLEAN NOT NULL,
    CONSTRAINT fk_states_country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE
);

CREATE TABLE cities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    state_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    erased BOOLEAN NOT NULL,
    CONSTRAINT fk_cities_state FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE
);

CREATE TABLE address (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nickname VARCHAR(32),
    street VARCHAR(64) NOT NULL,
    number VARCHAR(15) NOT NULL,
    suburb VARCHAR(32) NOT NULL,
    zip_code VARCHAR(64) NOT NULL,
    reference VARCHAR(128),
    country_id UUID NOT NULL,
    state_id UUID NOT NULL,
    city_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_address_country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE,
    CONSTRAINT fk_address_state FOREIGN KEY (state_id) REFERENCES states(id) ON DELETE CASCADE,
    CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE
);


CREATE TABLE legal_representative (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(128) NOT NULL,
    rfc VARCHAR(13) NOT NULL,
    address_id UUID,
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_legal_representative_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name CHARACTER VARYING(128) NOT NULL,
    description CHARACTER VARYING(256) NOT NULL,
    email CHARACTER VARYING(32) NOT NULL,
    phone CHARACTER VARYING(20) NOT NULL,
    address_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_company_address FOREIGN KEY (address_id) REFERENCES address (id)
);


CREATE TABLE taxpayer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    corporate_reason_or_natural_person VARCHAR(128) NOT NULL,
    rfc VARCHAR(13) NOT NULL,
    type_person VARCHAR(12) NOT NULL,
    legal_representative_id UUID UNIQUE,
    address_id UUID NOT NULL,
    company_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_taxpayer_legal_representative FOREIGN KEY (legal_representative_id) REFERENCES legal_representative(id),
    CONSTRAINT fk_taxpayer_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_taxpayer_company FOREIGN KEY (company_id) REFERENCES company (id)
);