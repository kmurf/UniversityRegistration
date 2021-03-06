package ie.kevmurf.universityregistration.services;

import ie.kevmurf.oas.model.ProfessorApiSpec;
import ie.kevmurf.universityregistration.data.model.Professor;
import ie.kevmurf.universityregistration.data.model.UniversityClass;
import ie.kevmurf.universityregistration.data.repositories.ProfessorRepository;
import ie.kevmurf.universityregistration.data.repositories.UniversityClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private static final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UniversityClassRepository universityClassRepository;

    public boolean update(Integer professorId, ProfessorApiSpec oasProfessor){
        Optional<Professor> userOpt = professorRepository.findById(professorId);
        if(userOpt.isPresent()) {
            Professor professor = userOpt.get();
            load(professor, oasProfessor);
            professorRepository.save(professor);
            return true;
        }
        return false;
    }

    public Professor create(ProfessorApiSpec inputProf){
        Professor professor = new Professor();
        professorRepository.save(professor);
        load(professor, inputProf);
        professorRepository.save(professor);
        return professor;
    }

    public void load(Professor profToLoad, ProfessorApiSpec inputProf){
        @NotNull String name = inputProf.getName();
        if(!StringUtils.isEmpty(name)){
            profToLoad.setName(name);
        }
        List<Integer> classIds = inputProf.getClassIds();
        if(classIds!=null) {
        	// to update a professors classes, first remove any current classes
        	profToLoad.getUniversityClassList().forEach(universityClass -> {
        		universityClass.setProfessor(null);
        		universityClassRepository.save(universityClass);
        	});
        	// then add new classes
            List<UniversityClass> universityClasses = new ArrayList<>(classIds.size());
            classIds.forEach(classId -> {
                Optional<UniversityClass> optClass = universityClassRepository.findById(classId);
                if (optClass.isPresent()) {
                    UniversityClass universityClass = optClass.get();
                    universityClass.setProfessor(profToLoad);
                    universityClassRepository.save(universityClass);
                    universityClasses.add(universityClass);
                }
            });
            profToLoad.setUniversityClassList(universityClasses);
        }
    }

}
