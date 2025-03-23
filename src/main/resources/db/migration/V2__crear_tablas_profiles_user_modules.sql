CREATE TABLE profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(64) NOT NULL UNIQUE,
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE modules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    route VARCHAR(255) NOT NULL,
    icon_name VARCHAR(255),
    "level" varchar(255) NULL,
	"description" varchar(255) NULL,
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    is_parent BOOLEAN NOT NULL,
    parent_id UUID,
    CONSTRAINT fk_modules_parent FOREIGN KEY (parent_id) REFERENCES modules(id) ON DELETE SET NULL
);

CREATE TABLE profile_modules (
    profile_id UUID NOT NULL,
    module_id UUID NOT NULL,
    PRIMARY KEY (profile_id, module_id),
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_module FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    erased BOOLEAN NOT NULL DEFAULT FALSE,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE user_profile (
    user_id UUID NOT NULL,
    profile_id UUID NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);
