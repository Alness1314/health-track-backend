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

CREATE TABLE files (
    id UUID PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    extension VARCHAR(64) NOT NULL,
    mime_type VARCHAR(128) NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    erased BOOLEAN NOT NULL DEFAULT FALSE
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


CREATE TABLE company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name CHARACTER VARYING(128) NOT NULL,
    description CHARACTER VARYING(256) NOT NULL,
    email CHARACTER VARYING(32) NOT NULL,
    phone CHARACTER VARYING(20) NOT NULL,
    address_id UUID NOT NULL,
    image_id UUID NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_company_address FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT fk_company_files FOREIGN KEY (image_id) REFERENCES files (id)
);

CREATE TABLE taxpayer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    corporate_reason_or_natural_person VARCHAR(256) NOT NULL,
    rfc VARCHAR(256) NOT NULL,
    type_person VARCHAR(12) NOT NULL,
    address_id UUID NOT NULL,
    data_key VARCHAR(64) NOT NULL,
    company_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_taxpayer_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE,
    CONSTRAINT fk_taxpayer_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);


CREATE TABLE legal_representative (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(256) NOT NULL,
    rfc VARCHAR(256) NOT NULL,
    data_key VARCHAR(64) NULL,
    taxpayer_id UUID NOT NULL,
    address_id UUID NOT NULL,
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_taxpayer FOREIGN KEY (taxpayer_id) REFERENCES taxpayer(id) ON DELETE CASCADE,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

CREATE TABLE subsidiary (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nickname VARCHAR(128) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(64),
    responsible VARCHAR(64),
    opening_hours VARCHAR(128),
    taxpayer_id UUID NOT NULL,
    address_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_subsidiary_taxpayer FOREIGN KEY (taxpayer_id) REFERENCES taxpayer(id) ON DELETE CASCADE,
    CONSTRAINT fk_subsidiary_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);