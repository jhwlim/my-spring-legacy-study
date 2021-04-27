DROP TABLE user_authorities;
DROP TABLE users;

CREATE TABLE users (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (username)
);

CREATE TABLE user_authorities (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id, authority),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
