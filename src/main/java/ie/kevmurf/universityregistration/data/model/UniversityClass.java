package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UniversityClass {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name= "PROFESSOR_ID")
    private Professor professor;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "universityClassList")
    private List<Student> studentList;

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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public ClassApiSpec asOasModel(){
        ClassApiSpec oasClass = new ClassApiSpec();
        oasClass.setId(id);
        oasClass.setName(name);
        if(professor!=null) {
            oasClass.setProfessorId(professor.getId());
        }

        ArrayList<Integer> studentIds = new ArrayList<>();
        if(studentList!=null) {
            studentList.forEach(student -> studentIds.add(student.getId()));
        }
        oasClass.setStudentIds(studentIds);
        return oasClass;
    }
}
