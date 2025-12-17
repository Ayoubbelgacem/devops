package tn.esprit.studentmanagement.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudent;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    // Constructeurs
    public Student() {}

    // Getters
    public Long getIdStudent() { return idStudent; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getAddress() { return address; }
    public Department getDepartment() { return department; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    // Setters
    public void setIdStudent(Long idStudent) { this.idStudent = idStudent; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setAddress(String address) { this.address = address; }
    public void setDepartment(Department department) { this.department = department; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}