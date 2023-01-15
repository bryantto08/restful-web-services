insert into user_details(id, birth_date, name) 
VALUES (10001, current_date(), 'Ranga');

insert into user_details(id, birth_date, name) 
VALUES (10002, current_date(), 'John');

insert into user_details(id, birth_date, name) 
VALUES (10003, current_date(), 'David');

insert into post(id, description, user_id) 
VALUES (20001, 'I want to learn AWS', 10001);

insert into post(id, description, user_id) 
VALUES (20002, 'I want to learn DevOps', 10001);

insert into post(id, description, user_id) 
VALUES (20003, 'I want to get AWS Certified', 10002);
