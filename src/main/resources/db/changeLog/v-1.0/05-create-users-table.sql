CREATE TABLE users (
      id          SERIAL PRIMARY KEY,
      name        VARCHAR(255) NOT NULL,
      role_id     INTEGER REFERENCES roles(id)
)