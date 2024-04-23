insert into students(grade, studyyear, facultysection, name, role) VALUES (8.76, 1, 0, 'John', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 0, 'Jane', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 1, 'Jack', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 1, 'Jill', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 1, 'James', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 1, 'Jenny', 'student');
insert into students(grade, studyyear, facultysection, name, role) VALUES (1, 1, 1, 'Jesse', 'student');

insert into admins(name) VALUES ('Ion');

insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('Math', 0, 1, 2, 'Mr. Smith');
insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('Physics', 0, 1, 2, 'Mr. Johnson');
insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('Chemistry', 3, 1, 2, 'Mr. Brown');
insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('Biology', 1, 1, 2, 'Mr. White');
insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('History', 1, 1, 2, 'Mr. Black');
insert into courses(name, facultysection, studyyear, maxcapacity, teacher) VALUES ('Geography', 1, 1, 2, 'Mr. Green');

insert into applications(studentid, courseid, status, priority) VALUES (1, 1, 2, 1);
insert into applications(studentid, courseid, status, priority) VALUES (1, 2, 2, 2);
insert into applications(studentid, courseid, status, priority) VALUES (2, 3, 2, 3);
insert into applications(studentid, courseid, status, priority) VALUES (1, 1, 2, 4);
insert into applications(studentid, courseid, status, priority) VALUES (1, 3, 2, 5);

select * from students;
select * from admins;
select * from courses;
select * from applications;

