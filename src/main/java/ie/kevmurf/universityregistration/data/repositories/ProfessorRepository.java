package ie.kevmurf.universityregistration.data.repositories;

import ie.kevmurf.universityregistration.data.model.Professor;
import ie.kevmurf.universityregistration.data.model.UniversityClass;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {

}