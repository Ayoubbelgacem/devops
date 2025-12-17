package tn.esprit.studentmanagement.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnrollment;
    private LocalDate enrollmentDate;
    private Double grade;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    // Constructeurs
    public Enrollment() {}

    // Getters
    public Long getIdEnrollment() { return idEnrollment; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Double getGrade() { return grade; }
    public Status getStatus() { return status; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }

    // Setters
    public void setIdEnrollment(Long idEnrollment) { this.idEnrollment = idEnrollment; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public void setGrade(Double grade) { this.grade = grade; }
    public void setStatus(Status status) { this.status = status; }
    public void setStudent(Student student) { this.student = student; }
    public void setCourse(Course course) { this.course = course; }
}