INSERT INTO Users (displayName, email) VALUES ('Ola Nordmann', 'olaNord@snagmail.com');
INSERT INTO Users (displayName, email) VALUES ('Kari Norddame', 'kari1989@snagmail.com');
INSERT INTO Users (displayName, email) VALUES ('Navn Navnesen', 'navn@navnesen.no');

--Got help from ChatGPT on how to hash the passwords with SHA-256, which is done in backend when adding
--a new user. If a new form of hashing is implemented, the inserts below and backend have to be aligned.
--An important improvement for the future is to add a salt to improve the security and protect against rainbow tables.
--Also tried to use SELECT inside VALUES, which did not work. ChatGPT came with the solution
--of using a SELECT instead of VALUES.
INSERT INTO UserCredentials (userID, password)
SELECT userID, SHA2('olaErBest!', 256) FROM Users WHERE email = 'olaNord@snagmail.com';

INSERT INTO UserCredentials (userID, password)
SELECT userID, SHA2('ef912hfs12', 256) FROM Users WHERE email = 'kari1989@snagmail.com';

INSERT INTO UserCredentials (userID, password)
SELECT userID, SHA2('Passord123', 256) FROM Users WHERE email = 'navn@navnesen.no';

INSERT INTO Category (categoryName) VALUES ('Fashion');
INSERT INTO Category (categoryName) VALUES ('Sports');
INSERT INTO Category (categoryName) VALUES ('Furniture');
INSERT INTO Category (categoryName) VALUES ('Electronics');
INSERT INTO Category (categoryName) VALUES ('Hobby');
INSERT INTO Category (categoryName) VALUES ('Other');

--kan fikse inserts på flyet på mandag?
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Piggvar the Porcupine',
        'It has been in my family for 500 years, but it is finally time for it to get some new owners.',
        5000, 9.551073155081776,59.26876460303738, 'piggvar.jpg', 3, 3);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Airpods',
        'These have been dropped on the floor several times. Works fine.',
        700, 10.400897686940299,63.40671219626866, 'airpods.jpg', 4, 1);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Bike Helmet',
        'Bought this for my husband, but he said he didn`t need them, and I`ve lost the receipt, so now i have to sell it.',
        1000, 10.440118066200379,59.78456300714556, 'bikeHelmet.jpg', 2, 2);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Red blanket',
        'Did not like the color red, and cannot be bothered to return it. Will sell this cheap.',
        50, 10.400897686940299,63.40671219626866, 'blanket.jpg', 3, 1);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Everdell Farshore',
        'It has been in my family for 5 years, but it is finally time for it to get a new owner who loves board games.',
        300, 9.551073155081776,59.26876460303738, 'boardgame.jpg', 5, 3);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('FIFA 2008',
        'This has been collecting dust. Works with PS3. Good luck.',
        100, 10.400897686940299,63.40671219626866, 'fifa.jpg', 5, 1);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Beer holder',
        'Got this at Kennedy Space Center for my husband, but he didn`t like it. I really do not understand why.',
        200, 10.440118066200379,59.78456300714556, 'holdMyBeer.jpg', 6, 2);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Gaming laptop - Asus ROG Zephyrus G16',
        'This was not good enough for the games i like to play. I will therefor sell it.',
        20000, 10.440118066200379,59.78456300714556, 'laptop.jpg', 4, 2);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Stylish lounge chair',
        'It has been in my family for 20 years, but it is finally time for it to get some new owners. It is great.',
        500, 9.551073155081776,59.26876460303738, 'loungechair.jpg', 3, 3);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Nice pillows',
        'I now have bought better pillows. Do not need these. Please buy.',
        150, 10.400897686940299,63.40671219626866, 'pillows.jpg', 3, 1);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Pretty plant',
        'It has been in my family for 5 months, but it is finally time for it to get some new owners. It smells great.',
        225, 9.551073155081776,59.26876460303738, 'plant.jpg', 6, 3);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Skechers',
        'I am selling some pretty great and comfortable shoes. These shoes are made for walkin`. And that`s just what they`ll do. One of these days, these shoes are gonna walk all over you.',
        500, 10.440118066200379,59.78456300714556, 'shoes.jpg', 1, 2);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Green sweater',
        'This is a very comfortable sweater.',
        50, 10.440118066200379,59.78456300714556, 'sweater.jpg', 1, 2);
INSERT INTO Item (shortDesc, longDesc, price, longitude, latitude, thumbnailPath, categID, userID)
VALUES ('Amazing television',
        'It has been in my family for 10 years, but it is finally time for it to get some new owners. I love watching Kompani Lauritzen on it.',
        3500, 9.551073155081776,59.26876460303738, 'television.jpg', 6, 3);

