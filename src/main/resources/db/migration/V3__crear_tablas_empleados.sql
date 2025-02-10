CREATE TABLE employee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    names VARCHAR(128) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    mother_last_name VARCHAR(64),
    birthay_date DATE NOT NULL,
    gender VARCHAR(64) NOT NULL,
    identification_number VARCHAR(256) NOT NULL,
    rfc VARCHAR(256) NOT NULL,
    nationality VARCHAR(64) NOT NULL,
    address_id UUID NOT NULL,
    user_id UUID NOT NULL,
    image_id UUID UNIQUE,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_employee_image FOREIGN KEY (image_id) REFERENCES files (id)
);

CREATE TABLE employee_subsidiary (
    id_user UUID NOT NULL,
    id_subsidiary UUID NOT NULL,
    PRIMARY KEY (id_user, id_subsidiary),
    CONSTRAINT fk_employee_subsidiary_employee FOREIGN KEY (id_user) REFERENCES employee (id),
    CONSTRAINT fk_employee_subsidiary_subsidiary FOREIGN KEY (id_subsidiary) REFERENCES subsidiary (id)
);
