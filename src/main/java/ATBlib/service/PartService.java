package ATBlib.service;

import ATBlib.model.*;
import ATBlib.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartService {

    private final UniRepository uniRepository;

    public Page<?> getParts(int pageIndex, int pageSize, MultiValueMap<String, String> rqParams) {
             return uniRepository.findAll((Specification<GlobalPart>) constructSpecification(rqParams), PageRequest.of(pageIndex, pageSize));
    }

    private static final String FILTER_MIN_PRICE = "min_price";
    private static final String FILTER_MAX_PRICE = "max_price";
    private static final String FILTER_TITLE = "title";

    private Specification<?> constructSpecification(MultiValueMap<String, String> params) {
        Specification<GlobalPart> spec = Specification.where(null);
        if (params.containsKey(FILTER_MIN_PRICE) && !Objects.requireNonNull(params.getFirst(FILTER_MIN_PRICE)).isBlank()) {
            int minPrice = Integer.parseInt(Objects.requireNonNull(params.getFirst(FILTER_MIN_PRICE)));
            spec = spec.and(PartSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (params.containsKey(FILTER_MAX_PRICE) && !Objects.requireNonNull(params.getFirst(FILTER_MAX_PRICE)).isBlank()) {
            int maxPrice = Integer.parseInt(Objects.requireNonNull(params.getFirst(FILTER_MAX_PRICE)));
            spec = spec.and(PartSpecifications.priceLesserOrEqualsThan(maxPrice));
        }
        if (params.containsKey(FILTER_TITLE) && !Objects.requireNonNull(params.getFirst(FILTER_TITLE)).isBlank()) {
            String title = params.getFirst(FILTER_TITLE);
            spec = spec.and(PartSpecifications.titleLike(title));
        }
        return spec;
    }

    public ResponseEntity<?> getPart(String partNumber) {
        Optional<?> retPart = Optional.empty();
        retPart = uniRepository.findByPartNumber(partNumber);

        if (retPart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(retPart.get());
    }

    public ResponseEntity<?> createPart(GlobalPart newPart) {
        Optional<?> existingPart = Optional.empty();
        existingPart = uniRepository.findByPartNumber(newPart.getPartNumber());

        if (existingPart.isPresent()) {
            log.info("User tried to add new part but it was already present.");
            return ResponseEntity.status(409).build();
        }

        String newPartNumber = newPart.getPartNumber();
        System.out.println("new part number: '" + newPartNumber + "'");
        if(newPartNumber.length() <= 3 || !newPartNumber.substring(0, 3).equals("_")) {
            log.info("User tried to add new part, but the Part Number was invalid.");
            return ResponseEntity.unprocessableEntity().body(newPart);
        }

        GlobalPart savedPart = uniRepository.save(newPart);

        URI newPartLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{partNumber}")
                .buildAndExpand(savedPart.getPartNumber())
                .toUri();

        log.info("User added new part '" + savedPart.getPartNumber() + "'.");
        return ResponseEntity.created(newPartLocation).build();

    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity<?> updatePart(GlobalPart updatedPart, String partNumber) {

        Optional<?> oldPart = Optional.empty();
        oldPart = uniRepository.findByPartNumber(partNumber);

        if (oldPart.isEmpty()) {
            log.info("User tried updating part '" + partNumber + "', but it was not found.");
            return ResponseEntity.notFound().build();
        }

        updatedPart.setPartNumber(partNumber);
        uniRepository.save(updatedPart);
        log.info("User updated part '" + partNumber + "'.");

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deletePart(String partNumber) {
        Optional<?> partOptional = Optional.empty();
        partOptional = uniRepository.findByPartNumber(partNumber);

        if (partOptional.isPresent()) {
            log.info("User deleted part '" + partNumber + "'.");
            uniRepository.deleteByPartNumber(partNumber);
            return ResponseEntity.ok().build();
        } else {
            log.info("User tried to delete part '" + partNumber + "', but it was not found.");
            return ResponseEntity.notFound().build();
        }
    }
}
