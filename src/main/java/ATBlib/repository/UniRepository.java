package ATBlib.repository;

import ATBlib.model.GlobalPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniRepository extends JpaRepository<GlobalPart, Long>, JpaSpecificationExecutor<GlobalPart> {
    Optional<GlobalPart> findByPartNumber(String partNumber);
    void deleteByPartNumber(String partNumber);
}
