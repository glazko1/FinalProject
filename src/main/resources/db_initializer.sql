CREATE DATABASE IF NOT EXISTS finalproject DEFAULT CHARACTER SET UTF8;

USE finalproject;

CREATE TABLE IF NOT EXISTS UserStatus (
  StatusId INT NOT NULL PRIMARY KEY,
  StatusName VARCHAR(20) NOT NULL
);

#Table to store information...
CREATE TABLE IF NOT EXISTS User (
  UserId BIGINT NOT NULL PRIMARY KEY,
  Username VARCHAR(15) NOT NULL,
  FirstName VARCHAR(30) NOT NULL,
  LastName VARCHAR(30) NOT NULL,
  Password VARCHAR(16) NOT NULL,
  StatusId INT NOT NULL,
  Email VARCHAR(50) NOT NULL,
  Banned TINYINT(1) NOT NULL DEFAULT FALSE,
  BirthDate DATE,
  FOREIGN KEY (StatusId) REFERENCES UserStatus(StatusId)
);

CREATE TABLE IF NOT EXISTS Movie (
  MovieId BIGINT NOT NULL PRIMARY KEY,
  Title VARCHAR(50) NOT NULL,
  RunningTime INT NOT NULL,
  Budget INT NOT NULL,
  ReleaseDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Alien (
  AlienId BIGINT NOT NULL PRIMARY KEY,
  AlienName VARCHAR(30) NOT NULL,
  MovieId BIGINT NOT NULL,
  Planet VARCHAR(50) NOT NULL,
  Description VARCHAR(255),
  AverageRating DOUBLE NOT NULL,
  ImagePath VARCHAR(30) NOT NULL,
  FOREIGN KEY (MovieId) REFERENCES Movie(MovieId)
);

CREATE TABLE IF NOT EXISTS Feedback (
  FeedbackId BIGINT NOT NULL PRIMARY KEY,
  AlienId BIGINT NOT NULL,
  UserId BIGINT NOT NULL,
  Rating INT NOT NULL,
  FeedbackText VARCHAR(255) NOT NULL,
  FeedbackDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId) ON DELETE CASCADE,
  FOREIGN KEY (UserId) REFERENCES User(UserId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Edit (
  EditId BIGINT NOT NULL PRIMARY KEY,
  AlienId BIGINT NOT NULL,
  UserId BIGINT NOT NULL,
  EditText VARCHAR(255) NOT NULL,
  EditDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId),
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE IF NOT EXISTS Notification (
  NotificationId BIGINT NOT NULL PRIMARY KEY,
  UserId BIGINT NOT NULL,
  NotificationText VARCHAR(255) NOT NULL,
  NotificationDateTime DATETIME NOT NULL,
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

INSERT INTO UserStatus VALUES (1, 'Admin');
INSERT INTO UserStatus VALUES (2, 'Movie Fan');
INSERT INTO UserStatus VALUES (3, 'Alien Specialist');
INSERT INTO UserStatus VALUES (4, 'User');

INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (1075300686238053249, 'glazko1', 'Yegor', 'Glazko', '�ڎ��L�/�K�詰�', 1, 'glazko_2001@mail.ru', FALSE, '2001-04-10');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (587605900484245118, 'ivanov1999', 'Petr', 'Ivanov', 'S��@Ǜ������D3', 2, 'ivanov1999@gmail.com', FALSE, '1999-10-24');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (4974415751458174531, 'sergeipetrov', 'Sergei', 'Petrov', 'Yب�}���8!pț�p', 3, 'sergeipetr0@yandex.ru', FALSE, '1984-12-15');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (8270790706125328316, 'alexandra234', 'Alexandra', 'Sidorova', ' ���@}%�%zw`˘', 4, 'alexandra1998@mail.ru', FALSE, '1998-05-31');
INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (7384731506407341452, 'codymontgomery', 'Cody', 'Montgomery', '�4d0-���ΫO��Z�', 4, 'mntgmr@yahoo.com', FALSE, '1990-06-10');

INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (1000863472395342653, 'Transformers', 143, 150000000, '2007-06-12');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (3309131760743718221, 'Star Trek', 132, 35000000, '1979-12-06');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (2599822088365269208, 'Avatar', 162, 246000000, '2009-12-10');

INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (1246084352581391226, 'Bumblebee', 1000863472395342653, 'Cybertron', 'Bumblebee is a fictional robot superhero from the Transformers franchise. Bumblebee is a small, yellow with black stripes Autobot, with most of his alternative vehicle modes inspired by several generations of the Chevrolet American muscle cars.', 0, 'img/1246084352581391226.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (3094259717952124046, 'Optimus Prime', 1000863472395342653, 'Cybertron', 'Optimus Prime is a robot superhero character from the Transformers robot superhero franchise. He is the leader of the Autobots, a group of sentient self-configuring modular extraterrestrial robotic lifeforms (e.g.: cars and other objects).', 0, 'img/3094259717952124046.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (2942137616530107072, 'Worf', 3309131760743718221, 'Kronos', 'Worf is a fictional character in the Star Trek franchise. Worf is the first Klingon main character to appear in Star Trek, and has appeared in more Star Trek franchise episodes than any other character.', 0, 'img/2942137616530107072.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (4191784467028363431, 'Neytiri', 2599822088365269208, 'Pandora', 'Neytiri te Tskaha Moatite is the Navi princess of the Omaticaya clan. She is the second-born daughter of Eytukan and Mo''at and younger sister of Sylwanin. She will be the future Tsahìk of the clan, with her mate, Jake, as Oloeyktan.', 0, 'img/4191784467028363431.png');
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating, ImagePath) VALUES (5192042131618303553, 'Spock', 3309131760743718221, 'Vulcan', 'Spock is a fictional character in the Star Trek media franchise. Spock serves aboard the starship Enterprise as commanding officer of two vessel iterations. Spock is mixed human-Vulcan heritage serves as an important plot element in many appearances.', 0, 'img/5192042131618303553.png');

INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (2161105905448246429, 5192042131618303553, 4974415751458174531, 5, 'Really best fictional character!', '2019-01-12 22:48:05');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (2830890913015566235, 3094259717952124046, 7384731506407341452, 5, 'Watched all films with him! I am a big fan of OPTIMUS PRIME)', '2019-02-25 16:55:44');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (4038641592799943946, 1246084352581391226, 587605900484245118, 5, 'He is very cool!', '2019-02-25 16:49:54');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (5662780650299075152, 3094259717952124046, 587605900484245118, 4, 'Good alien!', '2019-02-17 00:00:00');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (6255580097498026364, 1246084352581391226, 8270790706125328316, 5, 'Love him soooo much', '2019-02-24 18:43:30');
INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (9110481480489117472, 3094259717952124046, 1075300686238053249, 5, 'He is my favorite superhero :)', '2019-02-24 18:31:41');
