
CREATE TABLE author (id BIGINT IDENTITY PRIMARY KEY , firstName VARCHAR(50) NOT NULL, secondName VARCHAR(50) NOT NULL, thirdName VARCHAR(50) NOT NULL)

CREATE TABLE genre (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL)

CREATE TABLE book (id BIGINT IDENTITY PRIMARY KEY , name VARCHAR(50) NOT NULL, author BIGINT NOT NULL, genre BIGINT NOT NULL, publisher VARCHAR(50) NOT NULL, year DATE NOT NULL, city VARCHAR(50) NOT NULL, FOREIGN KEY (author) REFERENCES author(id) ON DELETE RESTRICT ON UPDATE CASCADE, FOREIGN KEY (genre) REFERENCES genre(id) ON DELETE RESTRICT ON UPDATE CASCADE)

INSERT INTO author (firstName, secondName, thirdName) VALUES('Евгений','Барабанов','Андреевич')

INSERT INTO genre (name) VALUES('Хорор')

INSERT INTO book (name, author, genre, publisher, year, city) VALUES('Книга',0,0,'Москва', NOW(),'Самара')
