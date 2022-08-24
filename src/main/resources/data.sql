INSERT IGNORE INTO role (role_id, workers_role) VALUES (1, 'Admin');
INSERT IGNORE INTO role (role_id, workers_role) VALUES (2, 'User');

INSERT IGNORE INTO worker (workers_id,workers_active,workers_email,workers_last_name,workers_name, workers_password, role_id)
VALUES (1, true, 'tomek.ziola@gmail.com','Zioła','Tomasz', 'bb', 1);