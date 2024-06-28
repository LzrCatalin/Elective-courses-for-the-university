export class Application {
    id: number | undefined;
    studentId: number | undefined;
    courseId: number | undefined;
    priority: number | undefined;
    status: string | undefined;

    constructor(id: number, studentId: number, courseId: number, priority: number, status: string) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.priority = priority;
        this.status = status;
    }
}