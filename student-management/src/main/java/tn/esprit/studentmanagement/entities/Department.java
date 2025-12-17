package tn.esprit.studentmanagement.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartment;
    private String name;
    private String location;
    private String phone;
    private String head;

    @OneToMany(mappedBy = "department")
    private List<Student> students;

    // Constructeurs
    public Department() {}

    public Department(Long idDepartment, String name, String location, String phone, String head) {
        this.idDepartment = idDepartment;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.head = head;
    }

    // Getters
    public Long getIdDepartment() { return idDepartment; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPhone() { return phone; }
    public String getHead() { return head; }
    public List<Student> getStudents() { return students; }

    // Setters
    public void setIdDepartment(Long idDepartment) { this.idDepartment = idDepartment; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setHead(String head) { this.head = head; }
    public void setStudents(List<Student> students) { this.students = students; }
}