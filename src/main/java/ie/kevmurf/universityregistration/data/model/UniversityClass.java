package ie.kevmurf.universityregistration.data.model;

import ie.kevmurf.oas.model.ClassApiSpec;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UniversityClass {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    public UniversityClass(){}

    public UniversityClass(ClassApiSpec oasClass){
        setId(oasClass.getId());
        setName(oasClass.getName());
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

    public ClassApiSpec asOasModel(){
        ClassApiSpec oasClass = new ClassApiSpec();
        oasClass.setId(id);
        oasClass.setName(name);
        return oasClass;
    }

    public void update(ClassApiSpec oasClass){
        setName(oasClass.getName());
    }
}
