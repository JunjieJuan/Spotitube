DROP DATABASE IF EXISTS Spotitube;
CREATE DATABASE Spotitube;
USE Spotitube;

CREATE TABLE users (
                       id       INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       token    VARCHAR(50)
);

CREATE TABLE playlist (
                          id      INT AUTO_INCREMENT PRIMARY KEY,
                          name    VARCHAR(50) NOT NULL,
                          user_id INT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE track (
                       id               INT AUTO_INCREMENT PRIMARY KEY,
                       title            VARCHAR(50)  NOT NULL,
                       performer        VARCHAR(50)  NOT NULL,
                       url              VARCHAR(200) NOT NULL,
                       duration         INT          NOT NULL,
                       album            VARCHAR(50),
                       playcount        INT,
                       publicationDate  DATE,
                       description      VARCHAR(500)
);

CREATE TABLE playlist_track (
                                playlist_id      INT NOT NULL,
                                track_id         INT NOT NULL,
                                offlineAvailable BOOLEAN NOT NULL DEFAULT FALSE,
                                PRIMARY KEY (playlist_id, track_id),
                                FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
                                FOREIGN KEY (track_id)    REFERENCES track(id)    ON DELETE CASCADE
);

INSERT INTO users (username, password) VALUES
                                           ('user', 'wachtwoord'),
                                           ('junjie', 'wachtwoord');

INSERT INTO playlist (name, user_id) VALUES
                                         ('rock', 1),
                                         ('edm', 2);

INSERT INTO track (title, performer, url, duration, album) VALUES
                                                               ('American Idiot', 'Green Day', 'https://example.com/american_idiot', 176, 'American Idiot'),
                                                               ('Jesus of Suburbia', 'Green Day', 'https://example.com/jesus_of_suburbia', 540, 'American Idiot'),
                                                               ('Holiday', 'Green Day', 'https://example.com/holiday', 212, 'American Idiot'),
                                                               ('Boulevard of Broken Dreams', 'Green Day', 'https://example.com/boulevard_of_broken_dreams', 258, 'American Idiot'),
                                                               ('Are We the Waiting', 'Green Day', 'https://example.com/are_we_the_waiting', 173, 'American Idiot'),
                                                               ('St. Jimmy', 'Green Day', 'https://example.com/st_jimmy', 156, 'American Idiot'),
                                                               ('Give Me Novacaine', 'Green Day', 'https://example.com/give_me_novacaine', 201, 'American Idiot'),
                                                               ('Homecoming', 'Green Day', 'https://example.com/homecoming', 462, 'American Idiot'),
                                                               ('Whatsername', 'Green Day', 'https://example.com/whatsername', 186, 'American Idiot');

INSERT INTO playlist_track (playlist_id, track_id, offlineAvailable) VALUES
                                                                         (1, 1, false),
                                                                         (1, 2, true),
                                                                         (1, 3, true),
                                                                         (1, 4, false),
                                                                         (1, 5, true),
                                                                         (1, 6, true),
                                                                         (1, 7, false),
                                                                         (1, 8, false),
                                                                         (1, 9, false);

INSERT INTO track (title, performer, url, duration, album) VALUES
                                                               ('Animals', 'Martin Garrix', 'https://example.com/animals', 285, 'Animals'),
                                                               ('Scared to Be Lonely', 'Martin Garrix', 'https://example.com/scared_to_be_lonely', 207, 'Scared to Be Lonely'),
                                                               ('In the Name of Love', 'Martin Garrix', 'https://example.com/in_the_name_of_love', 227, 'In the Name of Love'),
                                                               ('High on Life', 'Martin Garrix', 'https://example.com/high_on_life', 215, 'High on Life'),
                                                               ('There for You', 'Martin Garrix', 'https://example.com/there_for_you', 198, 'There for You');

INSERT INTO playlist_track (playlist_id, track_id, offlineAvailable) VALUES
                                                                         (2, 10, false),
                                                                         (2, 11, true),
                                                                         (2, 12, true),
                                                                         (2, 13, true),
                                                                         (2, 14, false);