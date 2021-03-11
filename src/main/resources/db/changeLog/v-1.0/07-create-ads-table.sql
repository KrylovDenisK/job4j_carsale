CREATE TABLE ads (
    id            SERIAL PRIMARY KEY,
    car_id        INTEGER REFERENCES cars(id),
    date          TIMESTAMP,
    status_id     INTEGER REFERENCES statuses(id),
    user_id       INTEGER REFERENCES users(id)
)