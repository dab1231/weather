create table users
(
    id SERIAL primary key,
    login VARCHAR(64) UNIQUE NOT NULL ,
    password VARCHAR(128) NOT NULL
);

create table locations
(
    id SERIAL primary key ,
    name VARCHAR(128) NOT NULL ,
    user_id INT REFERENCES users,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6)
);

create table sessions
(
    id uuid primary key ,
    user_id INT REFERENCES users,
    expires_at timestamp
);