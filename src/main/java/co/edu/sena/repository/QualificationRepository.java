package co.edu.sena.repository;

import co.edu.sena.domain.Qualification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Qualification entity.
 */
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    default Optional<Qualification> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Qualification> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Qualification> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct qualification from Qualification qualification left join fetch qualification.contactData",
        countQuery = "select count(distinct qualification) from Qualification qualification"
    )
    Page<Qualification> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct qualification from Qualification qualification left join fetch qualification.contactData")
    List<Qualification> findAllWithToOneRelationships();

    @Query("select qualification from Qualification qualification left join fetch qualification.contactData where qualification.id =:id")
    Optional<Qualification> findOneWithToOneRelationships(@Param("id") Long id);
}
