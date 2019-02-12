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
  FOREIGN KEY (MovieId) REFERENCES Movie(MovieId)
);

CREATE TABLE IF NOT EXISTS Feedback (
  FeedbackId BIGINT NOT NULL PRIMARY KEY,
  AlienId BIGINT NOT NULL,
  UserId BIGINT NOT NULL,
  Rating INT NOT NULL,
  FeedbackText VARCHAR(255) NOT NULL,
  FeedbackDateTime DATETIME NOT NULL,
  FOREIGN KEY (AlienId) REFERENCES Alien(AlienId),
  FOREIGN KEY (UserId) REFERENCES User(UserId)
);

INSERT into UserStatus VALUES (1, 'Admin');
INSERT into UserStatus VALUES (2, 'Movie Fan');
INSERT into UserStatus VALUES (3, 'Alien Specialist');
INSERT into UserStatus VALUES (4, 'User');

INSERT INTO User (UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate) VALUES (12, 'glazko1', 'Yegor', 'Glazko', 1, 'glazko_2001@mail.ru', FALSE, '2001-04-10');
INSERT INTO User (UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate) VALUES (154, 'ivanov1999', 'Petr', 'Ivanov', 2, 'ivanov1999@gmail.com', FALSE, '1999-10-24');
INSERT INTO User (UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate) VALUES (58, 'sergeipetrov', 'Sergei', 'Petrov', 3, 'sergeipetr0@yandex.ru', FALSE, '1984-12-15');
INSERT INTO User (UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate) VALUES (68, 'alexandra234', 'Alexandra', 'Sidorova', 4, 'alexandra1998@mail.ru', FALSE, '1998-07-01');

INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (7, 'Transformers', 143, 150000000, '2007-06-12');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (38, 'Star Trek', 132, 35000000, '1979-12-06');
INSERT INTO Movie (MovieId, Title, RunningTime, Budget, ReleaseDate) VALUES (26, 'Avatar', 162, 246000000, '2009-12-10');

INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating) VALUES (90, 'Optimus Prime', 7, 'Cybertron', 'Optimus Prime is a robot superhero character from the Transformers robot superhero franchise. He is the leader of the Autobots, a group of sentient self-configuring modular extraterrestrial robotic lifeforms (e.g.: cars and other objects).', 0);
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating) VALUES (147, 'Worf', 38, 'Kronos', 'Worf is a fictional character in the Star Trek franchise. Worf is the first Klingon main character to appear in Star Trek, and has appeared in more Star Trek franchise episodes than any other character.', 0);
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating) VALUES (52, 'Neytiri', 26, 'Pandora', 'Neytiri te Tskaha Moatite is the Navi princess of the Omaticaya clan. She is the second-born daughter of Eytukan and Mo''at and younger sister of Sylwanin. She will be the future Tsahìk of the clan, with her mate, Jake, as Oloeyktan.', 0);
INSERT INTO Alien (AlienId, AlienName, MovieId, Planet, Description, AverageRating) VALUES (241, 'Spock', 38, 'Vulcan', 'Spock is a fictional character in the Star Trek media franchise. Spock serves aboard the starship Enterprise as commanding officer of two vessel iterations. Spock is mixed human-Vulcan heritage serves as an important plot element in many appearances.', 0);

INSERT INTO Feedback (FeedbackId, AlienId, UserId, Rating, FeedbackText, FeedbackDateTime) VALUES (14, 241, 58, 5, 'Really best fictional character!', '2019-01-12 22:48:05');