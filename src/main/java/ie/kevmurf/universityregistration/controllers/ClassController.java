package ie.kevmurf.universityregistration.controllers;

import ie.kevmurf.oas.api.ClassesApi;
import ie.kevmurf.oas.model.ClassApiSpec;
import ie.kevmurf.universityregistration.data.model.UniversityClass;
import ie.kevmurf.universityregistration.data.repositories.UniversityClassRepository;
import ie.kevmurf.universityregistration.services.UniversityClassService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api")
public class ClassController implements ClassesApi {

    private static final Logger log = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private UniversityClassRepository universityClassRepository;

    @Autowired
    private UniversityClassService universityClassService;

    @Override
    public ResponseEntity<ClassApiSpec> createClass(@ApiParam(value = ""  )  @Valid @RequestBody ClassApiSpec body
    ) {
        UniversityClass saved = universityClassService.create(body);
        return new ResponseEntity<ClassApiSpec>(saved.asOasModel(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteClass(@Min(1)@ApiParam(value = "The ID of the class to delete.",required=true, allowableValues="") @PathVariable("classId") Integer classId
    ) {
        universityClassRepository.deleteById(classId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClassApiSpec> getClassById(@Min(1)@ApiParam(value = "The ID of the class to retrieve.",required=true, allowableValues="") @PathVariable("classId") Integer classId
    ) {
        Optional<UniversityClass> optional = universityClassRepository.findById(classId);
        if(optional.isPresent()){
            return new ResponseEntity<ClassApiSpec>(optional.get().asOasModel(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<ClassApiSpec>> getClasses() {
        ArrayList<ClassApiSpec> list = new ArrayList<>();
        universityClassRepository.findAll().forEach(universityClass -> list.add(universityClass.asOasModel()));
        return new ResponseEntity<List<ClassApiSpec>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateClass(@Min(1)@ApiParam(value = "The ID of the class to update.",required=true, allowableValues="") @PathVariable("classId") Integer classId
            ,@ApiParam(value = ""  )  @Valid @RequestBody ClassApiSpec body
    ) {
        System.out.println("ClassController.updateClass()");
        if(universityClassService.update(classId, body)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
