1. users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

explanation :

BIGSERIAL PRIMARY KEY
→ Auto-generated unique user ID
→ Better than UUID for DB joins & performance (interview-friendly)

username, email UNIQUE
→ Prevent duplicate accounts
→ Enforced at DB level (very important)

password VARCHAR(255)
→ Stores hashed password, never plain text

is_active
→ Soft-disable users without deleting data

created_at, updated_at
→ Audit fields (enterprise standard)


----------------------------------------------
2. roles table

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

----------------------------------------------
3. user_roles (Many-to-Many mapping)

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id)
);
