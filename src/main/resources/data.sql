INSERT INTO teams  (team_name) VALUES ('A-Team');
INSERT INTO teams  (team_name) VALUES ('B-Team');
INSERT INTO users (user_id, user_name, password, team_id) VALUES('taro.yamada@gmail.com','山田 太郎', 'password', 1);
INSERT INTO users (user_id, user_name, password, team_id) VALUES('saburo.yamada@gmail.com','山田 三郎', 'password', 1);
INSERT INTO users (user_id, user_name, password, team_id) VALUES('jiro.yamada@gmail.com','山田 次郎', 'password', 2);
INSERT INTO users (user_id, user_name, password, team_id) VALUES('shiro.yamada@gmail.com','山田 史郎', 'password', 2);


INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('XX機能要件書作成',2,'2025-10-31',1);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('YY機能ユースケース',2,'2025-10-29',2);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('ZZ機能E-R図作成',2,'2025-10-28',3);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('AAAA機能要件書作成',2,'2025-10-21',4);


INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('XX定義書作成',1,'2025-11-13',1);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('YY定義書作成',1,'2025-11-09',2);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('ZZテーブル定義書作成',1,'2025-11-14',3);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('クラス図作成',1,'2025-11-04',4);


INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('XX設計書作成',0,'2025-11-28',1);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('YY設計書作成',0,'2025-11-29',2);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('ZZ設計書作成',0,'2025-11-26',3);
INSERT INTO tasks (task_content, status, due_date, user_id) VALUES('AAAA設計書作成',2,'2025-11-20',4);
