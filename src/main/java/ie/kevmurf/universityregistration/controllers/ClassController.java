package ie.kevmurf.universityregistration.controllers;

import ie.kevmurf.oas.api.ClassesApi;
import ie.kevmurf.oas.model.ClassApiSpec;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping(value = "/api")
public class ClassController implements ClassesApi {

    private static final Logger log = LoggerFactory.getLogger(ClassController.class);

    @Override
    public ResponseEntity<ClassApiSpec> createClass(@ApiParam(value = ""  )  @Valid @RequestBody ClassApiSpec body
    ) {
        return new ResponseEntity<ClassApiSpec>(createSample(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteClass(@Min(1)@ApiParam(value = "The ID of the class to delete.",required=true, allowableValues="") @PathVariable("classId") Integer classId
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClassApiSpec> getClassById(@Min(1)@ApiParam(value = "The ID of the class to retrieve.",required=true, allowableValues="") @PathVariable("classId") Integer classId
    ) {
        return new ResponseEntity<ClassApiSpec>(createSample(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClassApiSpec>> getClasses() {
        ArrayList<ClassApiSpec> list = new ArrayList<>();
        list.add(createSample());
        return new ResponseEntity<List<ClassApiSpec>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateClass(@Min(1)@ApiParam(value = "The ID of the class to update.",required=true, allowableValues="") @PathVariable("classId") Integer classId
            ,@ApiParam(value = ""  )  @Valid @RequestBody ClassApiSpec body
    ) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ClassApiSpec createSample(){
        ClassApiSpec sample = new ClassApiSpec();
        sample.setId(1);
        sample.setName("History");
        return sample;
    }
}
