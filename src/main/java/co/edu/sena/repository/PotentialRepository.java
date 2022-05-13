package co.edu.sena.repository;

import co.edu.sena.domain.Potential;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Potential entity.
 */
@Repository
public interface PotentialRepository extends JpaRepository<Potential, Long> {
    default Optional<Potential> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Potential> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Potential> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct potential from Potential potential left join fetch potential.contactData",
        countQuery = "select count(distinct potential) from Potential potential"
    )
    Page<Potential> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct potential from Potential potential left join fetch potential.contactData")
    List<Potential> findAllWithToOneRelationships();

    @Query("select potential from Potential potential left join fetch potential.contactData where potential.id =:id")
    Optional<Potential> findOneWithToOneRelationships(@Param("id") Long id);
}
