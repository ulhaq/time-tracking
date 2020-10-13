CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

SELECT setval('users_id_seq', 3, true);