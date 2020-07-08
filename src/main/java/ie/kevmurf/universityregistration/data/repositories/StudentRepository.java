package ie.kevmurf.universityregistration.data.repositories;

import ie.kevmurf.universityregistration.data.model.Student;
import ie.kevmurf.universityregistration.data.model.UniversityClass;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}