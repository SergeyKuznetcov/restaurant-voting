INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, ADDRESS)
VALUES ('restaurant1', 'address1'),
       ('restaurant2', 'address2'),
       ('restaurant3', 'address3');

INSERT INTO MENU (NAME, DATE, RESTAURANT_ID)
VALUES ('menu1', '2020-01-30', 1),
       ('menu2', '2020-01-30', 2),
       ('menu3', '2020-01-30', 3);

INSERT INTO MEAL (NAME, PRICE, MENU_ID)
VALUES ('meal1', 100, 1),
       ('meal2', 200, 1),
       ('meal3', 300, 2),
       ('meal4', 400, 2),
       ('meal5', 500, 3),
       ('meal6', 600, 3);

INSERT INTO MENU_USERS (USER_ID, MENU_ID)
VALUES (1, 1),
       (2, 2);
