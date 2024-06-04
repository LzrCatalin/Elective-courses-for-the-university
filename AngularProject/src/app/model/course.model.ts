
export class Course {
    id: number | undefined;
    name: string | undefined;
    category: string | undefined;
    studyYear: number | undefined;
    teacher: string | undefined;
    maxCapacity: number | undefined;
    facultySection: string | undefined;
    applicationsCount: number | undefined;

    constructor(id: number, name: string, category: string, studyYear: number, teacher: string, maxCapacity: number, facultySection: string, applicationsCount: number) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.studyYear = studyYear;
        this.teacher = teacher;
        this.maxCapacity = maxCapacity;
        this.facultySection = facultySection;
        this.applicationsCount = applicationsCount;
    }

}