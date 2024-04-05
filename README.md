# Elective-courses-for-the-university
Web-based application where each university student can apply for elective courses which are available for the current year of study

Old Diagram:
![InitialEntitiesDiagram](https://github.com/LzrCatalin/Elective-courses-for-the-university/assets/118479914/93e66003-fb6f-45e9-90d3-acd7d555e80e)

Updated Diagram:
![image](https://github.com/LzrCatalin/Elective-courses-for-the-university/assets/118479914/a39ae0d4-6bc6-462b-a3ef-055c3939595e)

Diagram Updates:

- Added "user" table.
- Implemented camelCase naming convention.
- Added the "application" table, which could function as the enrollments table. Through SQL queries, it will be possible to retrieve the number of students enrolled in a course based on their status, reassign students to another course, and retrieve students enrolled in the same course.

- Removed: M:N relationships, i.e: Enrollments, Student ElectiveChoices and Application Preference tables.
- Removed: Notations as "Student_ID", "Course_ID" from tables as PK, now are define simply as "id"

Questions:
1. Do we need a table for admins? We think not, because we don't have any additional information to store about an admin. Instead, based on the userRole, we can determine if a user is a student or an admin.

2. Is it possible to have an admin-student role?

