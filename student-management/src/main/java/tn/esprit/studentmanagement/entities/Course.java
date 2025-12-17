package tn.esprit.studentmanagement.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;
    private String name;
    private String code;
    private int credit;
    private String description;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    // Constructeurs
    public Course() {}

    // Getters
    public Long getIdCourse() { return idCourse; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public int getCredit() { return credit; }
    public String getDescription() { return description; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    // Setters
    public void setIdCourse(Long idCourse) { this.idCourse = idCourse; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setCredit(int credit) { this.credit = credit; }
    public void setDescription(String description) { this.description = description; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}