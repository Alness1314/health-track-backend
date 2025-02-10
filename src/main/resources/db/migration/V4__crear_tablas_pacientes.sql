CREATE TABLE patients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(128) NOT NULL,
    birthay_date DATE NOT NULL,
    curp VARCHAR(256),
    rfc VARCHAR(256),
    age INTEGER NOT NULL,
    gender VARCHAR(64) NOT NULL,
    marital_status VARCHAR(64),
    nationality VARCHAR(64) NOT NULL,
    address_id UUID NOT NULL,
    phone VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    occupation VARCHAR(64),
    emergency_contact VARCHAR(64) NOT NULL,
    emergency_phone VARCHAR(64) NOT NULL,
    relationship VARCHAR(64),
    user_id UUID NOT NULL,
    create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    update_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_patients_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_patients_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE medical_record (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reason_consultation VARCHAR(256) NOT NULL,
    beginning_evolution_state VARCHAR(1024) NOT NULL,
    symptoms_start_date DATE NOT NULL,
    patient_id UUID UNIQUE NOT NULL,
    CONSTRAINT fk_medical_record_patient FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE TABLE patients_subsidiary (
    id_patients UUID NOT NULL,
    id_subsidiary UUID NOT NULL,
    PRIMARY KEY (id_patients, id_subsidiary),
    CONSTRAINT fk_patients_subsidiary_patients FOREIGN KEY (id_patients) REFERENCES patients(id) ON DELETE CASCADE,
    CONSTRAINT fk_patients_subsidiary_subsidiary FOREIGN KEY (id_subsidiary) REFERENCES subsidiary(id) ON DELETE CASCADE
);
