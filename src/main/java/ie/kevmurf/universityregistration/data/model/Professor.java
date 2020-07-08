package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.ProfessorApiSpec;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.PERSIST)
    private List<UniversityClass> universityClassList;

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

    public ProfessorApiSpec asOasModel(){
        ProfessorApiSpec oasProfessor = new ProfessorApiSpec();
        oasProfessor.setId(id);
        oasProfessor.setName(name);

        ArrayList<Integer> classIds = new ArrayList<>();
        if(universityClassList!=null) {
            universityClassList.forEach(universityClass -> classIds.add(universityClass.getId()));
        }
        oasProfessor.setClassIds(classIds);
        return oasProfessor;
    }

}
