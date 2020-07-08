package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.StudentApiSpec;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Student_Classes",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "class_id")}
    )
    private List<UniversityClass> universityClassList;

    public Student(){}

    public Student(StudentApiSpec oasStudent){
        setId(oasStudent.getId());
        setName(oasStudent.getName());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UniversityClass> getUniversityClassList() {
        return universityClassList;
    }

    public void setUniversityClassList(List<UniversityClass> universityClassList) {
        this.universityClassList = universityClassList;
    }

    public StudentApiSpec asOasModel(){
        StudentApiSpec oasStudent = new StudentApiSpec();
        oasStudent.setId(id);
        oasStudent.setName(name);

        ArrayList<Integer> classIds = new ArrayList<>();
        if(universityClassList!=null) {
            universityClassList.forEach(universityClass -> classIds.add(universityClass.getId()));
        }
        oasStudent.setClassIds(classIds);
        return oasStudent;
    }

    public void update(StudentApiSpec oasStudent){
        setName(oasStudent.getName());
    }
}
