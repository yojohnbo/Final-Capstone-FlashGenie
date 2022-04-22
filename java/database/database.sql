BEGIN TRANSACTION;

DROP TABLE IF EXISTS cards, decks, users, user_details;


DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE decks (
	deck_id serial NOT NULL,
	user_id int NOT NULL,
	deck_name varchar(50) NOT NULL,
	click_count int DEFAULT 0,
	subject varchar(50) NOT NULL,
	CONSTRAINT PK_deck PRIMARY KEY (deck_id),
	CONSTRAINT FK_deck_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE cards (
	card_id serial NOT NULL,
	deck_id int NOT NULL,
	front_text varchar(50) NOT NULL,
	back_text varchar(300) NOT NULL,
	keywords varchar(50) NOT NULL,
	CONSTRAINT PK_card PRIMARY KEY (card_id),
	CONSTRAINT FK_deck FOREIGN KEY(deck_id) REFERENCES decks(deck_id)
);

CREATE TABLE user_details (
	user_id int NOT NULL,
	email varchar(254) NOT NULL,
	CONSTRAINT PK_email PRIMARY KEY (email),
	CONSTRAINT FK_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

-- Sets deck_id serial to 1 again --
ALTER SEQUENCE decks_deck_id_seq RESTART WITH 1;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('test','$2a$10$L4S7LccUIOgyDIW4dZlCfO8BpDzSxk4rtAdQB/Ly7dZarQjfKQljK','ROLE_USER');

--- USER SETUP (Do Not Modify)
DROP USER IF EXISTS final_capstone_owner;
DROP USER IF EXISTS final_capstone_appuser;

CREATE USER final_capstone_owner
WITH PASSWORD 'finalcapstone';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO final_capstone_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_owner;

CREATE USER final_capstone_appuser
WITH PASSWORD 'finalcapstone';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO final_capstone_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_appuser;

--User setup above this line 

COMMIT TRANSACTION;

START TRANSACTION;

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Java', 'Programming');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Zelda Series', 'Video Games');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Political Systems', 'History');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'SAT Words', 'Vocabulary');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Botany', 'Plants');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Technology', 'IT');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Stars', 'Astrology');

INSERT INTO decks (user_id, deck_name, subject)
VALUES (3, 'Basketball', 'Sports');

COMMIT;

START TRANSACTION;

-- Deck 1
INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES(1, 'Encapsulation', 'The concept of hiding values or the state of data in a class and limiting the points of access.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Inheritance', 'The practice of creating a hierarchy for classes in which descendants obtain the attributes and behaviors of their parents/grandparents with the ability to override.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES(1, 'Polymorphism', 'The ability for code to take different forms. Treating classes generically and getting specific results (like a blueprint that creates one kind of building. Structurally those buildings are the same kind of building, but they may have a different number of windows or may be painted different colors.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Classes', 'Blueprints for creating objects.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Unit Test', 'Testing methods and classes to make sure your code works as expected and produces accurate and repeatable results.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'HTTP Request and Response', 'How data is exchanged between serverr and client. Requests are sent by client to trigger an action on the server - responses are the answer back to the client.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Authorization', 'Permissions to access data such as user roles.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Authentication', 'Verifying identity like logging in, 2FA, and other ownership factors.', 'Java');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (1, 'Null Pointer Exception', 'Runtime error - when a variable is accessed and is not pointing to any object and refers to nothing or null.', 'Java');

-- Deck 2
INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (2, 'Boko', 'The term "Boko" is used in the name of a number of forest-related objects. Among them are Boko Sticks, Boko Babas and Boko Baba Pods. The Bokoblins, famous for wielding Boko Sticks, have also inspired their name on it.', 'Video Games');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (2, 'ChuChus', 'The collective name for a wide variety of blob-like enemies that appear in many games. Each ChuChu has its own unique traits and abilities, but most are weak to certain items or attacks.', 'Video Games');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (2, 'Death Mountain', ' A large mountain that is the home of Dodongo and other fire-based enemies. It can be found in several different games, which often has a town of Gorons near its base.', 'Video Games');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (2, 'Great Fairy', 'A fairy that appears in several games as a guide to Link. Unlike their Pendant counterparts, they do not award Link with useful items or upgrades; instead, they simply provide him with additional health or magic power.', 'Video Games');

-- Deck 3
INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Capitalism', 'An economic system where most companies, land and other resources are privately owned.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Communism', 'A political-economic system with no state, minimal class differences and economic equality.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Democracy', 'A political system where the government or parts of it are selected by the people.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Imperialism', 'A system where a powerful state conquers territories (colonies) for its own gain.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Socialism', 'A system where the government rules in the interests of the workers or common people.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Fascism', 'A political system marked by authoritarian rule, nationalism, state and military power.', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Colonialism', 'A system of claiming, settling, ruling and maintaining one or more colonies (see imperialism).', 'History');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (3, 'Absolutism', 'Any political system where the ruler or government wields absolute power.', 'History');

-- Deck 4

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Histrionic', 'Characteristic of acting or stage performance.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Infallible', 'Incapable of failure or error.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Maladroit', 'Showing lack of skill; clumsy or awkward.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Loquacious', 'Talkative; given to continual talking; chattering.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Malefactor', 'Criminal; one who does evil or injury to another.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Martinet', 'strict disciplinarian; one who demands absolute adherence to forms and rules.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Levity', 'Lack of seriousness; lightness of manner or speech, especially when inappropriate.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Lithe', 'Flexible; moving and bending with ease.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Harangue', 'Noisy speech; speech or piece of writing with strong feeling or expression.', 'Vocabulary');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (4, 'Incipient', 'Beginning to exist or appear; in an early stage.', 'Vocabulary');

-- Deck 5
INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Annual', 'A plant that completes its life cycle within one calendar year. It goes from seed to seed in one growing season.', 'Plants');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Cellulose', 'The substance in plant cells that makes plants rigid and aids in keeping plants from collapsing.', 'Plants');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Stem', 'The main axis of a plant, the central stalk.', 'Plants');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Submersed Plant', 'Plants that develop completely under the surface of water. Leaves and flowers may eventually grow above the water surface.', 'Plants');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Rosette', 'The beginning formation of a plant after the seedling stage. It is a grouping of basal leaves.', 'Plants');

INSERT INTO cards (deck_id, front_text, back_text, keywords)
VALUES (5, 'Rhizome', 'A root-like stem that grows horizontally underground and produces new leaves and shoots.', 'Plants');



COMMIT;