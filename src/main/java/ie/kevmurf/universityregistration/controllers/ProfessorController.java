package ie.kevmurf.universityregistration.controllers;

import ie.kevmurf.oas.api.ProfessorsApi;
import ie.kevmurf.oas.model.ProfessorApiSpec;
import ie.kevmurf.universityregistration.data.model.Professor;
import ie.kevmurf.universityregistration.data.repositories.ProfessorRepository;
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
public class ProfessorController implements ProfessorsApi {

    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    @Autowired
    private ProfessorRepository universityProfessorRepository;

    @Override
    public ResponseEntity<ProfessorApiSpec> createProfessor(@ApiParam(value = ""  )  @Valid @RequestBody ProfessorApiSpec body
    ) {
        Professor saved = universityProfessorRepository.save(new Professor(body));
        return new ResponseEntity<ProfessorApiSpec>(saved.asOasModel(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProfessor(@Min(1)@ApiParam(value = "The ID of the professor to delete.",required=true, allowableValues="") @PathVariable("professorId") Integer professorId
    ) {
        universityProfessorRepository.deleteById(professorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfessorApiSpec> getProfessorById(@Min(1)@ApiParam(value = "The ID of the professor to retrieve.",required=true, allowableValues="") @PathVariable("professorId") Integer professorId
    ) {
        Optional<Professor> optional = universityProfessorRepository.findById(professorId);
        if(optional.isPresent()){
            return new ResponseEntity<ProfessorApiSpec>(optional.get().asOasModel(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<ProfessorApiSpec>> getProfessors() {
        ArrayList<ProfessorApiSpec> list = new ArrayList<>();
        universityProfessorRepository.findAll().forEach(universityProfessor -> list.add(universityProfessor.asOasModel()));
        return new ResponseEntity<List<ProfessorApiSpec>>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateProfessor(@Min(1)@ApiParam(value = "The ID of the professor to update.",required=true, allowableValues="") @PathVariable("professorId") Integer professorId
            ,@ApiParam(value = ""  )  @Valid @RequestBody ProfessorApiSpec body
    ) {
        Optional<Professor> userOpt = universityProfessorRepository.findById(professorId);
        if(userOpt.isPresent()) {
            Professor universityProfessor = userOpt.get();
            universityProfessor.update(body);
            universityProfessorRepository.save(universityProfessor);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
