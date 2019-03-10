CREATE DATABASE IF NOT EXISTS finalproject DEFAULT CHARACTER SET UTF8;

USE finalproject;

CREATE TABLE IF NOT EXISTS UserStatus (
  StatusId INT NOT NULL PRIMARY KEY,
  StatusName VARCHAR(20) NOT NULL
);

#Table to store information...
CREATE TABLE IF NOT EXISTS User (
  UserId BIGINT NOT NULL UNIQUE PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS Movie (
  MovieId BIGINT NOT NULL UNIQUE PRIMARY KEY,
  Title VARCHAR(30) NOT NULL UNIQUE,
  RunningTime INT NOT NULL,
  Budget INT NOT NULL,
  ReleaseDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Alien (
  AlienId BIGINT NOT NULL UNIQUE PRIMARY KEY,
  AlienName VARCHAR(30) NOT NULL UNIQUE,
  MovieId BIGINT NOT NULL,
  Planet VARCHAR(20) NOT NULL,
  Description VARCHAR(255),
  AverageRating DOUBLE NOT NULL,
  ImagePath VARCHAR(30) NOT NULL,
  FOREIGN KEY (MovieId) REFERENCES Movie(MovieId)
);

CREATE TABLE IF NOT EXISTS Feedback (
  FeedbackId BIGINT NOT NULL UNIQUE PRIMARY KEY,
  AlienId BIGINT NOT NULL,
  UserId BIGINT NOT NULL,
  Rating INT NOT NULL,
  FeedbackText VARCHAR(255) NOT NULL,
  FeedbackDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId) ON DELETE CASCADE,
  FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Edit (
  EditId BIGINT NOT NULL UNIQUE PRIMARY KEY,
  AlienId BIGINT NOT NULL,
  UserId BIGINT NOT NULL,
  EditText VARCHAR(255) NOT NULL,
  EditDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId),
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE IF NOT EXISTS Notification (
  NotificationId BIGINT NOT NULL UNIQUE PRIMARY KEY,
  UserId BIGINT NOT NULL,
  NotificationText VARCHAR(255) NOT NULL,
  NotificationDateTime DATETIME NOT NULL,
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

INSERT INTO UserStatus VALUES (1, 'Admin');
INSERT INTO UserStatus VALUES (2, 'Movie Fan');
INSERT INTO UserStatus VALUES (3, 'Alien Specialist');
INSERT INTO UserStatus VALUES (4, 'User');

INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (1466107775764106130, 'glazko1', 'Yegor', 'Glazko', '�ڎ��L�/�K�詰�', 1, 'glazko_2001@mail.ru', FALSE, '2001-04-10');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (3361155931186399386, 'ivanov1999', 'Petr', 'Ivanov', 'S��@Ǜ������D3', 2, 'ivanov1999@gmail.com', FALSE, '1999-10-24');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (5702512301127486073, 'sergeipetrov', 'Sergei', 'Petrov', 'Yب�}���8!pț�p', 3, 'sergeipetr0@yandex.ru', FALSE, '1984-12-15');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (6202045533747684552, 'alexandra234', 'Alexandra', 'Sidorova', ' ���@}%�%zw`˘', 4, 'alexandra1998@mail.ru', FALSE, '1998-05-31');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (6425215755539236776, 'codymontgomery', 'Cody', 'Montgomery', '�4d0-���ΫO��Z�', 4, 'mntgmr@yahoo.com', FALSE, '1990-06-10');

INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (42458794694295309, 'Transformers', 143, 150000000, '2007-06-12');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (3521898023800189852, 'Star Trek', 132, 35000000, '1979-12-06');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (4883861430713460259, 'Avatar', 162, 246000000, '2009-12-10');

INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (509061320753401321, 'Bumblebee', 42458794694295309, 'Cybertron', 'Bumblebee is a fictional robot superhero from the Transformers franchise. Bumblebee is a small, yellow with black stripes Autobot, with most of his alternative vehicle modes inspired by several generations of the Chevrolet American muscle cars.', 5, 'img/509061320753401321.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (1310815129049327455, 'Optimus Prime', 42458794694295309, 'Cybertron', 'Optimus Prime is a robot superhero character from the Transformers robot superhero franchise. He is the leader of the Autobots, a group of sentient self-configuring modular extraterrestrial robotic lifeforms (e.g.: cars and other objects).', 5, 'img/1310815129049327455.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (2965782938540582911, 'Worf', 3521898023800189852, 'Kronos', 'Worf is a fictional character in the Star Trek franchise. Worf is the first Klingon main character to appear in Star Trek, and has appeared in more Star Trek franchise episodes than any other character.', 0, 'img/2965782938540582911.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (5884615481528696026, 'Neytiri', 4883861430713460259, 'Pandora', 'Neytiri te Tskaha Moatite is the Navi princess of the Omaticaya clan. She is the second-born daughter of Eytukan and Mo''at and younger sister of Sylwanin. She will be the future Tsahìk of the clan, with her mate, Jake, as Oloeyktan.', 0, 'img/5884615481528696026.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (8993334745318001002, 'Spock', 3521898023800189852, 'Vulcan', 'Spock is a fictional character in the Star Trek media franchise. Spock serves aboard the starship Enterprise as commanding officer of two vessel iterations. Spock is mixed human-Vulcan heritage serves as an important plot element in many appearances.', 5, 'img/8993334745318001002.png');

INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (232486500548390477, 8993334745318001002, 5702512301127486073, 5, 'Really best fictional character!', '2019-01-12 22:48:05');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (3745409044056601216, 1310815129049327455, 6202045533747684552, 5, 'Watched all films with him! I am a big fan of OPTIMUS PRIME)', '2019-02-25 16:55:44');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (4539118978096437606, 509061320753401321, 3361155931186399386, 5, 'He is very cool!', '2019-02-25 16:49:54');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (4560243658064957221, 1310815129049327455, 3361155931186399386, 5, 'Good alien!', '2019-02-17 00:00:00');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (4961433908631205391, 509061320753401321, 6425215755539236776, 5, 'Love him soooo much', '2019-02-24 18:43:30');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (8074345373093580710, 1310815129049327455, 1466107775764106130, 5, 'He is my favorite superhero :)', '2019-02-24 18:31:41');

DROP TABLE Edit;
DROP TABLE Notification;
DROP TABLE Feedback;
DROP TABLE Alien;
DROP TABLE User;
DROP TABLE Movie;
DROP TABLE UserStatus;