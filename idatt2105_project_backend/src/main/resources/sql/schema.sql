--Linjene under gjør at tabellene droppes hver gang serveren starter opp.
--Dette gjøres pga. test-dataen som skal legges inn. Dersom man ikke ønsker dette
--kan man kjøre serveren én gang, få tabellene med test-data generert, og deretter slette
--filene for å ikke legge til ny testdata.
--Høre med C om vi burde gjøre dette annereldes.
DROP TABLE IF EXISTS UserCredentials;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Category;

CREATE TABLE Users (
    userID      int                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
    displayName varchar(255)        NOT NULL,
    email       varchar(255) UNIQUE NOT NULL
);

CREATE TABLE UserCredentials (
    userID      int                 NOT NULL,
    password varchar(255)           NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users (userID)
);

CREATE TABLE Category (
    categID     int                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
    categoryName varchar(63)        NOT NULL
);

CREATE TABLE Item (
    itemID      int                 NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shortDesc   varchar(127)        NOT NULL,
    longDesc    varchar(1023)       NOT NULL,
    price       int                 NOT NULL,
    longitude  double               NOT NULL,
    latitude    double              NOT NULL,
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    isForSale     BOOLEAN DEFAULT true,
    thumbnailPath varchar(511)      NOT NULL,
    categID     int                 NOT NULL,
    userID      int                 NOT NULL,
    FOREIGN KEY (categID) REFERENCES Category (categID),
    FOREIGN KEY (userID) REFERENCES Users (userID)
);





