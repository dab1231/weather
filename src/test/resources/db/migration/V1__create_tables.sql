create table users
(
    id INT AUTO_INCREMENT primary key,
    login VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
);

create table locations
(
    id INT AUTO_INCREMENT primary key,
    name VARCHAR(128) NOT NULL,
    user_id INT REFERENCES users ON DELETE CASCADE,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    UNIQUE(name, user_id)
);

create table sessions
(
    id uuid primary key,
    user_id INT REFERENCES users ON DELETE CASCADE,
    expires_at timestamp
);