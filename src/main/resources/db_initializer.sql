CREATE DATABASE IF NOT EXISTS finalproject DEFAULT CHARACTER SET UTF8;

USE finalproject;

#Table to store information about user statuses.
CREATE TABLE IF NOT EXISTS UserStatus (
  StatusId INT NOT NULL PRIMARY KEY,
  StatusName VARCHAR(20) NOT NULL
);

#Table to store information about users.
CREATE TABLE IF NOT EXISTS User (
  UserId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  Username VARCHAR(15) NOT NULL UNIQUE,
  FirstName VARCHAR(30) NOT NULL,
  LastName VARCHAR(30) NOT NULL,
  Password VARCHAR(16) NOT NULL,
  StatusId INT NOT NULL,
  Email VARCHAR(50) NOT NULL UNIQUE,
  Banned TINYINT(1) NOT NULL DEFAULT FALSE,
  BirthDate DATE,
  FOREIGN KEY (StatusId) REFERENCES UserStatus(StatusId)
);

#Table to store information about movies.
CREATE TABLE IF NOT EXISTS Movie (
  MovieId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  Title VARCHAR(30) NOT NULL UNIQUE,
  RunningTime INT NOT NULL,
  Budget INT NOT NULL,
  ReleaseDate DATE NOT NULL
);

#Table to store information about aliens.
CREATE TABLE IF NOT EXISTS Alien (
  AlienId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  AlienName VARCHAR(30) NOT NULL UNIQUE,
  MovieId VARCHAR(36) NOT NULL,
  Planet VARCHAR(20) NOT NULL,
  Description VARCHAR(255),
  AverageRating DOUBLE NOT NULL,
  ImagePath VARCHAR(30) NOT NULL,
  FOREIGN KEY (MovieId) REFERENCES Movie(MovieId)
);

#Table to store information about feedbacks.
CREATE TABLE IF NOT EXISTS Feedback (
  FeedbackId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  AlienId VARCHAR(36) NOT NULL,
  UserId VARCHAR(36) NOT NULL,
  Rating INT NOT NULL,
  FeedbackText VARCHAR(255) NOT NULL,
  FeedbackDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId) ON DELETE CASCADE,
  FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE CASCADE
);

#Table to store information about edits.
CREATE TABLE IF NOT EXISTS Edit (
  EditId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  AlienId VARCHAR(36) NOT NULL,
  UserId VARCHAR(36) NOT NULL,
  EditText VARCHAR(255) NOT NULL,
  EditDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId),
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

#Table to store information about notifications.
CREATE TABLE IF NOT EXISTS Notification (
  NotificationId VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,
  UserId VARCHAR(36) NOT NULL,
  NotificationText VARCHAR(255) NOT NULL,
  NotificationDateTime DATETIME NOT NULL,
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

INSERT INTO UserStatus VALUES (1, 'Admin');
INSERT INTO UserStatus VALUES (2, 'Movie Fan');
INSERT INTO UserStatus VALUES (3, 'Alien Specialist');
INSERT INTO UserStatus VALUES (4, 'User');

INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), 'glazko1', 'Yegor', 'Glazko', '�ڎ��L�/�K�詰�', 1, 'glazko_2001@mail.ru', FALSE, '2001-04-10');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), 'ivanov1999', 'Petr', 'Ivanov', 'S��@Ǜ������D3', 2, 'ivanov1999@gmail.com', FALSE, '1999-10-24');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), 'sergeipetrov', 'Sergei', 'Petrov', 'Yب�}���8!pț�p', 3, 'sergeipetr0@yandex.ru', FALSE, '1984-12-15');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), 'alexandra234', 'Alexandra', 'Sidorova', ' ���@}%�%zw`˘', 4, 'alexandra1998@mail.ru', FALSE, '1998-05-31');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), 'codymontgomery', 'Cody', 'Montgomery', '�4d0-���ΫO��Z�', 4, 'mntgmr@yahoo.com', FALSE, '1990-06-10');

INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (UUID(), 'Transformers', 143, 150000000, '2007-06-12');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (UUID(), 'Star Trek', 132, 35000000, '1979-12-06');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (UUID(), 'Avatar', 162, 246000000, '2009-12-10');

INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (UUID(), 'Bumblebee', 'da85533a-4c9a-11e9-bb52-18dbf22fb890', 'Cybertron', 'Bumblebee is a fictional robot superhero from the Transformers franchise. Bumblebee is a small, yellow with black stripes Autobot, with most of his alternative vehicle modes inspired by several generations of the Chevrolet American muscle cars.', 5, 'img/Bumblebee.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (UUID(), 'Optimus Prime', 'da85533a-4c9a-11e9-bb52-18dbf22fb890', 'Cybertron', 'Optimus Prime is a robot superhero character from the Transformers robot superhero franchise. He is the leader of the Autobots, a group of sentient self-configuring modular extraterrestrial robotic lifeforms (e.g.: cars and other objects).', 5, 'img/Optimus Prime.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (UUID(), 'Worf', 'da879c0a-4c9a-11e9-bb52-18dbf22fb890', 'Kronos', 'Worf is a fictional character in the Star Trek franchise. Worf is the first Klingon main character to appear in Star Trek, and has appeared in more Star Trek franchise episodes than any other character.', 0, 'img/Worf.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (UUID(), 'Neytiri', 'da8b5a35-4c9a-11e9-bb52-18dbf22fb890', 'Pandora', 'Neytiri te Tskaha Moatite is the Navi princess of the Omaticaya clan. She is the second-born daughter of Eytukan and Mo''at and younger sister of Sylwanin. She will be the future Tsahìk of the clan, with her mate, Jake, as Oloeyktan.', 0, 'img/Neytiri.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (UUID(), 'Spock', 'da879c0a-4c9a-11e9-bb52-18dbf22fb890', 'Vulcan', 'Spock is a fictional character in the Star Trek media franchise. Spock serves aboard the starship Enterprise as commanding officer of two vessel iterations. Spock is mixed human-Vulcan heritage serves as an important plot element in many appearances.', 5, 'img/Spock.png');

INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21e74c26-4c9b-11e9-bb52-18dbf22fb890', 'da834215-4c9a-11e9-bb52-18dbf22fb890', 5, 'Really best fictional character!', '2019-01-12 22:48:05');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21de8920-4c9b-11e9-bb52-18dbf22fb890', 'da8143a7-4c9a-11e9-bb52-18dbf22fb890', 5, 'Watched all films with him! I am a big fan of OPTIMUS PRIME)', '2019-02-25 16:55:44');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21cb6808-4c9b-11e9-bb52-18dbf22fb890', 'da7eb5c3-4c9a-11e9-bb52-18dbf22fb890', 5, 'He is very cool!', '2019-02-25 16:49:54');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21de8920-4c9b-11e9-bb52-18dbf22fb890', 'da7eb5c3-4c9a-11e9-bb52-18dbf22fb890', 5, 'Good alien!', '2019-02-17 00:00:00');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21cb6808-4c9b-11e9-bb52-18dbf22fb890', 'da834215-4c9a-11e9-bb52-18dbf22fb890', 5, 'Love him soooo much', '2019-02-24 18:43:30');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (UUID(), '21de8920-4c9b-11e9-bb52-18dbf22fb890', 'da7c4ede-4c9a-11e9-bb52-18dbf22fb890', 5, 'He is my favorite superhero :)', '2019-02-24 18:31:41');