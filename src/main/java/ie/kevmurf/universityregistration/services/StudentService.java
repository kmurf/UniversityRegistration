package ie.kevmurf.universityregistration.services;

import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.oas.model.StudentApiSpec;
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
public class StudentService {

    @Autowired
    private UniversityClassRepository universityClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    public boolean update(Integer studentId, StudentApiSpec oasStudent){
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if(studentOpt.isPresent()) {
            Student student = studentOpt.get();
            load(student, oasStudent);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    public Student create(StudentApiSpec inputStudent){
        Student student = new Student();
        load(student, inputStudent);
        studentRepository.save(student);
        return student;
    }

    public void load(Student studentToLoad, StudentApiSpec inputStudent){
        @NotNull String name = inputStudent.getName();
        if(!StringUtils.isEmpty(name)) {
            studentToLoad.setName(name);
        }
        List<Integer> classIds = inputStudent.getClassIds();
        if(classIds!=null) {
            List<UniversityClass> universityClasses = new ArrayList<>(classIds.size());
            classIds.forEach(classId -> {
                Optional<UniversityClass> optClass = universityClassRepository.findById(classId);
                if (optClass.isPresent()) {
                    UniversityClass universityClass = optClass.get();
                    universityClasses.add(universityClass);
                }
            });
            studentToLoad.setUniversityClassList(universityClasses);
        }
    }

}
