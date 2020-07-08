package ie.kevmurf.universityregistration.services;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.ProfessorApiSpec;
import ie.kevmurf.universityregistration.data.model.Professor;
import ie.kevmurf.universityregistration.data.model.Student;
import ie.kevmurf.universityregistration.data.model.UniversityClass;
import ie.kevmurf.universityregistration.data.repositories.ProfessorRepository;
import ie.kevmurf.universityregistration.data.repositories.StudentRepository;
import ie.kevmurf.universityregistration.data.repositories.UniversityClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityClassService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UniversityClassRepository universityClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    public boolean update(Integer classId, ClassApiSpec oasClass){
        Optional<UniversityClass> universityClassOpt = universityClassRepository.findById(classId);
        if(universityClassOpt.isPresent()) {
            UniversityClass uniClass = universityClassOpt.get();
            load(uniClass, oasClass);
            universityClassRepository.save(uniClass);

            return true;
        }
        return false;
    }

    public UniversityClass create(ClassApiSpec inputClass){
        UniversityClass universityClass = new UniversityClass();
        load(universityClass, inputClass);
        universityClassRepository.save(universityClass);
        return universityClass;
    }

    public void load(UniversityClass uniClassToLoad, ClassApiSpec inputUniClass){
        @NotNull String name = inputUniClass.getName();
        if(!StringUtils.isEmpty(name)) {
            uniClassToLoad.setName(name);
        }
        Integer professorId = inputUniClass.getProfessorId();
        if(professorId!=null) {
            if(professorId == -1){
                uniClassToLoad.setProfessor(null);
            }
            Optional<Professor> optProf = professorRepository.findById(professorId);
            if (optProf.isPresent()) {
                uniClassToLoad.setProfessor(optProf.get());
            }
        }
        List<Integer> studentIds = inputUniClass.getStudentIds();
        if(studentIds!=null){
            ArrayList<Student> students = new ArrayList<>(studentIds.size());
            studentIds.forEach(studentId -> {
                Optional<Student> optStudent = studentRepository.findById(studentId);
                if(optStudent.isPresent()){
                    students.add(optStudent.get());
                }
            });
            uniClassToLoad.setStudentList(students);
        }
    }

}
