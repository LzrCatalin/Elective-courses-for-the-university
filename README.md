# Elective-courses-for-the-university
Web-based application where each university student can apply for elective courses which are available for the current year of study

Entities Diagram:
![image](https://github.com/LzrCatalin/Elective-courses-for-the-university/assets/118479914/b677b714-32ac-4069-a7e0-abc8e031fa9e)

Implementation updates:
1. Refactored 'User' into an abstract class with protected attributes and marked it as @MappedSuperclass.
2. Subclasses 'Student' and 'Admin' inherit attributes from the abstract class 'User'.
3. Updated package names for improved consistency.

