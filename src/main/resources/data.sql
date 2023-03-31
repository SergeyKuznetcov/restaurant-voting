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
       ('menu3', '2020-01-30', 3),
       ('menu4', '2020-02-01', 1);

INSERT INTO MEAL (NAME, PRICE, RESTAURANT_ID)
VALUES ('restaurant1meal1', 100, 1),
       ('restaurant1meal2', 200, 1),
       ('restaurant2meal1', 300, 2),
       ('restaurant2meal2', 400, 2),
       ('restaurant3meal1', 500, 3),
       ('restaurant3meal2', 600, 3);

INSERT INTO MENU_MEAL (MENU_ID, MEAL_ID)
VALUES (1, 1),
        (1, 2),
        (2, 3),
        (2, 4),
        (3, 5),
        (3, 6),
        (4, 1),
        (4, 2);

INSERT INTO MENU_USERS (MENU_ID, USER_ID, VOTE_DATE_TIME)
VALUES (1, 1, '2020-01-30 19:14:20'),
       (2, 2, '2020-01-30 19:13:00');
