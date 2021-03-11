CREATE TABLE models (
      id          SERIAL PRIMARY KEY,
      name        VARCHAR(255) NOT NULL,
      brand_id    INTEGER REFERENCES brands(id),
      body_id     INTEGER REFERENCES body(id)
)