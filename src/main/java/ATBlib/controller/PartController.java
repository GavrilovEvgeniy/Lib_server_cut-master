package ATBlib.controller;

import ATBlib.model.GlobalPart;
import ATBlib.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@CrossOrigin( origins = {"http://localhost:3000"} )

public class PartController {

    @Autowired
    private PartService partService;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping(value = "/parts/table/{tabNumber}", headers = "Accept=application/json")
    public Page<?> getParts(@PathVariable int tabNumber, @RequestParam(defaultValue = "1", name = "p") int pageIndex,
            @RequestParam MultiValueMap<String, String> params) {
        if (pageIndex < 1) pageIndex = 1;
        return partService.getParts(pageIndex - 1, 20, params);
    }

    @GetMapping(value = "/parts/{partNumber}/{tabNumber}", headers = "Accept=application/json")
    public ResponseEntity<?> getPart(@PathVariable int tabNumber, @PathVariable String partNumber) {
        return partService.getPart(partNumber);
    }

    @PostMapping("/parts/{tabNumber}")
    public ResponseEntity<?> createPart(@PathVariable int tabNumber, @RequestBody GlobalPart newPart) {
        return partService.createPart(newPart);
    }

    @PutMapping("/parts/{partNumber}/{tabNumber}")
    public ResponseEntity<?> updatePart(@PathVariable int tabNumber, @RequestBody GlobalPart updatedPart, @PathVariable String partNumber) {
        return partService.updatePart(updatedPart, partNumber);
    }
    
    @DeleteMapping("/parts/{partNumber}/{tabNumber}")
    public ResponseEntity<?> deletePart(@PathVariable int tabNumber, @PathVariable String partNumber) {
        return partService.deletePart(partNumber);
    }

}
