package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.StudentApiSpec;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

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

    public StudentApiSpec asOasModel(){
        StudentApiSpec oasStudent = new StudentApiSpec();
        oasStudent.setId(id);
        oasStudent.setName(name);
        return oasStudent;
    }

    public void update(StudentApiSpec oasStudent){
        setName(oasStudent.getName());
    }
}
