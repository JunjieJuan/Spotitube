DROP DATABASE IF EXISTS Spotitube;
CREATE DATABASE Spotitube;
USE Spotitube;

CREATE TABLE users (
                       id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(50) NOT NULL,
                       token    VARCHAR(50) NULL
);

CREATE TABLE playlist (
                          id      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          name    VARCHAR(50) NOT NULL,
                          user_id INT         NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE track (
                       id               INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       title            VARCHAR(50)  NOT NULL,
                       performer        VARCHAR(50)  NOT NULL,
                       url              VARCHAR(200) NOT NULL,
                       duration         INT          NOT NULL,
                       album            VARCHAR(50)  NULL,
                       publicationDate  DATE         NULL,
                       description      VARCHAR(500) NULL,
                       offlineAvailable BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE playlist_track (
                                playlist_id INT NOT NULL,
                                track_id    INT NOT NULL,
                                PRIMARY KEY (playlist_id, track_id),
                                FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
                                FOREIGN KEY (track_id)    REFERENCES track(id)    ON DELETE CASCADE
);

-- Testdata
INSERT INTO users (username, password) VALUES
                                           ('user', 'wachtwoord'),
                                           ('junjie', 'wachtwoord');

INSERT INTO playlist (name, user_id) VALUES
                                         ('hiphop', 1),
                                         ('rnb', 1),
                                         ('rock', 1),
                                         ('relaxing', 2),
                                         ('edm', 2);

INSERT INTO track (title, performer, url, duration, album, offlineAvailable) VALUES
                                                                                 ('Song for someone', 'The Frames', 'https://example.com/1', 350, 'The cost', false),
                                                                                 ('The cost', 'The Frames', 'https://example.com/2', 423, null, true);

INSERT INTO track (title, performer, url, duration, publicationDate, description, offlineAvailable) VALUES
    ('One', 'Metallica', 'https://example.com/3', 423, '2001-03-18', 'Long version', true);