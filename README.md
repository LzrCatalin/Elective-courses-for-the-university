# Elective-courses-for-the-university
Web-based application where each university student can apply for elective courses which are available for the current year of study

Entities Diagram:
![image](https://github.com/LzrCatalin/Elective-courses-for-the-university/assets/118479914/b677b714-32ac-4069-a7e0-abc8e031fa9e)

Implementation updates:
1. **Fixing Cascade Deletion Issue**: Resolved a problem where deleting an application would inadvertently delete associated student and course IDs. The solution involved removing the CascadeType.ALL annotation from the @ManyToOne annotation, ensuring that only the IDs linked to the deleted application are removed automatically.

2. **Handling Enum Constraints**: Dealt with limitations linked to with enums by implementing the @Enumerated(EnumType.STRING) annotation. This solution facilitates the reordering of values and the addition of new ones without encountering errors. However, direct modification of enum values remains restricted. Source: https://www.baeldung.com/jpa-persisting-enums-in-jpa

3. **Adding Exceptions Handling**: Introduced a dedicated package for exceptions, containing most of the exceptions utilized in the project thus far.

4. **Updating Service Function Return Types**: Updated the return type of functions in services to no longer include ResponsiveEntity.


Questions:
1. Are the exception handling methods implemented ok? 
2. Have the issues with enums been resolved?
