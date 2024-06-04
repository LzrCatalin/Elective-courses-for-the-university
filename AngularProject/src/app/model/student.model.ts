export class Student {
    id: number | undefined;
    name: string | undefined;
    email: string | undefined;
    role: string | undefined;
    studyYear: number | undefined;
    grade: number | undefined;
    facultySection: string | undefined;

    constructor (id: number, name: string, email: string, role: string, studyYear: number, grade: number, facultySection: string) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.studyYear = studyYear;
        this.grade = grade;
        this.facultySection = facultySection;
    }
}