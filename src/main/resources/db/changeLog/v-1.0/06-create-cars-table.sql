CREATE TABLE cars (
        id          SERIAL PRIMARY KEY,
        model_id    INTEGER REFERENCES models(id),
        year        TIMESTAMP,
        photo       VARCHAR(255)
)