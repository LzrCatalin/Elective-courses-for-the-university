insert into users(name, role) values ('ion', 'admin');
insert into users(name, role) values ('gheorghe', 'student');

insert into students(grade, studyyear, userid, facultysection) values (10, 1, 2, 'info');

insert into courses(maxcapacity, studyyear, category, facultysection, name, teacher) values (10, 1, 'info', 'info', 'mate', 'ion');

insert into application(priority, courseid, userid, status) VALUES (1, 1, 2, 'pending');

select * from users;
select name, grade, studyyear, facultysection from students left join public.users u on u.id = students.userid;
select * from application;

