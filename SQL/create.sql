DROP DATABASE IF EXISTS Spotitube;

CREATE DATABASE Spotitube;

USE Spotitube;

CREATE TABLE User
(
    id			INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username	VARCHAR(50) NOT NULL UNIQUE,
    password	VARCHAR(50) NOT NULL,
    token		VARCHAR(50) NULL
);

CREATE TABLE Playlist (
	id		INT			NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name	VARCHAR(50)	NOT NULL,
	user_id	INT NOT NULL FOREIGN KEY REFERENCES User(id)
);

CREATE TABLE Track (
	id					INT				NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title				VARCHAR(50)		NOT NULL UNIQUE,
	performer			VARCHAR(50)		NOT NULL UNIQUE,
	duration			INT				NOT NULL,
	album				VARCHAR(50)		NULL UNIQUE,
	publicationDate		date			NULL,
	description			VARCHAR(500)	NULL UNIQUE,
	offlineAvailable	boolean			NOT NULL	
);

CREATE TABLE Playlist_Track(
	playlist_id	INT NOT NULL,
	track_id	INT NOT NULL,
	
	PRIMARY KEY (playlist_id, track_id),
	FOREIGN KEY (playlist_id) REFERENCES Playlist(id) ON DELETE CASCADE,
	FOREIGN KEY	(track_id) REFERENCES Track(id) ON DELETE CASCADE
);


/*TABLES INSERTEN*/
INSERT INTO User (username, password)
VALUES
('user', 'wachtwoord'),
('junjie', 'wachtwoord');

INSERT INTO Playlist (name, user_id)
VALUES
('hiphop', 1),
('rnb', 1),
('rock', 1),
('relaxing', 2),
('edm', 2);

/*songs*/
INSERT INTO Track (title, performer, url, duration, album, offlineAvaiable)
VALUES
();

/*videos*/
INSERT INTO Track (title, performer, url, duration, publicationDate, description, offlineAvaiable)
VALUES
();