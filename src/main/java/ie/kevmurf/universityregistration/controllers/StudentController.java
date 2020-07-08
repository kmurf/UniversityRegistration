package ie.kevmurf.universityregistration.controllers;

import ie.kevmurf.oas.api.StudentsApi;
import ie.kevmurf.oas.model.StudentApiSpec;
import ie.kevmurf.universityregistration.data.model.Student;
import ie.kevmurf.universityregistration.data.repositories.StudentRepository;
import ie.kevmurf.universityregistration.services.StudentService;
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
public class StudentController implements StudentsApi {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentRepository universityStudentRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public ResponseEntity<StudentApiSpec> createStudent(@ApiParam(value = ""  )  @Valid @RequestBody StudentApiSpec body
    ) {
        Student student = studentService.create(body);
        return new ResponseEntity<StudentApiSpec>(student.asOasModel(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(@Min(1)@ApiParam(value = "The ID of the student to delete.",required=true, allowableValues="") @PathVariable("studentId") Integer studentId
    ) {
        universityStudentRepository.deleteById(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StudentApiSpec> getStudentById(@Min(1)@ApiParam(value = "The ID of the student to retrieve.",required=true, allowableValues="") @PathVariable("studentId") Integer studentId
    ) {
        Optional<Student> optional = universityStudentRepository.findById(studentId);
        if(optional.isPresent()){
            return new ResponseEntity<StudentApiSpec>(optional.get().asOasModel(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<StudentApiSpec>> getStudents() {
        ArrayList<StudentApiSpec> list = new ArrayList<>();
        universityStudentRepository.findAll().forEach(universityStudent -> list.add(universityStudent.asOasModel()));
        return new ResponseEntity<List<StudentApiSpec>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateStudent(@Min(1)@ApiParam(value = "The ID of the student to update.",required=true, allowableValues="") @PathVariable("studentId") Integer studentId
            ,@ApiParam(value = ""  )  @Valid @RequestBody StudentApiSpec body
    ) {
        if(studentService.update(studentId, body)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
