INSERT IGNORE INTO role (role_id, worker_role) VALUES (1, 'Admin');
INSERT IGNORE INTO role (role_id, worker_role) VALUES (2, 'User');

INSERT IGNORE INTO worker (worker_id,worker_active,worker_email,worker_last_name,worker_name, worker_password, role_id)
VALUES (1, true, 'tomek.ziola@gmail.com','Zioła','Tomasz', '$2a$10$AuIvsds1i0hNA4SP.PQ9s.6BwFhs6aGVFP6lbb5crHhEfjZrlXCe2', 1);

INSERT IGNORE INTO animal (animal_id, animal_age, animal_city, animal_description, animal_health, animal_name, animal_race, animal_sex, animal_specie, image_id, worker_id)
VALUES (1,1,'Tychy', 'Szczeniak wymagający szczepienia', 'Bardzo dobry', 'Burek', 'Kundel', 'samiec', 'pies', 1, 1);

INSERT IGNORE INTO animal (animal_id, animal_age, animal_city, animal_description, animal_health, animal_name, animal_race, animal_sex, animal_specie, image_id, worker_id)
VALUES (2,5,'Katowice', 'Wymaga troski i opieki, problem z jedzeniem', 'Słaby, chudy', 'Kacper', 'Dachowiec', 'samica', 'kot', 2, 1);

INSERT IGNORE INTO animal (animal_id, animal_age, animal_city, animal_description, animal_health, animal_name, animal_race, animal_sex, animal_specie, image_id, worker_id)
VALUES (3,4,'Warszawa', 'Wesoły, przyjazny, boi się psów', 'Bardzo dobry', 'Brysio', 'Dachowiec', 'samiec', 'kot', 3, 1);

INSERT IGNORE INTO animal (animal_id, animal_age, animal_city, animal_description, animal_health, animal_name, animal_race, animal_sex, animal_specie, image_id, worker_id)
VALUES (4,8,'Kraków', 'Dla odpowiedzialnego i aktywnego człowieka', 'Bardzo dobry', 'Czarek', 'Owczarek niemiecki', 'samiec', 'pies', 4, 1);

INSERT IGNORE INTO animal (animal_id, animal_age, animal_city, animal_description, animal_health, animal_name, animal_race, animal_sex, animal_specie, image_id, worker_id)
VALUES (5,2,'Warszawa', 'Łagodny i pragnący pieszczot młody pies', 'Bardzo dobry', 'Reksio', 'Akita', 'samica', 'pies', 5, 1);

INSERT IGNORE INTO image (image_id, link_to_image) VALUES (1, 'https://s3.eu-central-1.amazonaws.com/shelterappphotos/TychyBurek1');

INSERT IGNORE INTO image (image_id, link_to_image) VALUES (2, 'https://s3.eu-central-1.amazonaws.com/shelterappphotos/KatowiceKacper5');

INSERT IGNORE INTO image (image_id, link_to_image) VALUES (3, 'https://s3.eu-central-1.amazonaws.com/shelterappphotos/WarszawaBrysio4');

INSERT IGNORE INTO image (image_id, link_to_image) VALUES (4, 'https://s3.eu-central-1.amazonaws.com/shelterappphotos/KrakówCzarek8');

INSERT IGNORE INTO image (image_id, link_to_image) VALUES (5, 'https://s3.eu-central-1.amazonaws.com/shelterappphotos/KrakówReksio2');