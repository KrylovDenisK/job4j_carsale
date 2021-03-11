CREATE TABLE cars (
        id          SERIAL PRIMARY KEY,
        brand_id    INTEGER REFERENCES brands(id),
        year        TIMESTAMP,
        photo       VARCHAR(255)
)