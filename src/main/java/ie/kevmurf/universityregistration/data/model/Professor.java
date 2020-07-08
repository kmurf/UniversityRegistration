package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.ProfessorApiSpec;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    public Professor(){}

    public Professor(ProfessorApiSpec oasProfessor){
        setId(oasProfessor.getId());
        setName(oasProfessor.getName());
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

    public ProfessorApiSpec asOasModel(){
        ProfessorApiSpec oasProfessor = new ProfessorApiSpec();
        oasProfessor.setId(id);
        oasProfessor.setName(name);
        return oasProfessor;
    }

    public void update(ProfessorApiSpec oasProfessor){
        setName(oasProfessor.getName());
    }
}
