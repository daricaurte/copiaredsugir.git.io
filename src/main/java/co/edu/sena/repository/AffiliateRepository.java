package co.edu.sena.repository;

import co.edu.sena.domain.Affiliate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Affiliate entity.
 */
@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {
    default Optional<Affiliate> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Affiliate> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Affiliate> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct affiliate from Affiliate affiliate left join fetch affiliate.user left join fetch affiliate.documentType",
        countQuery = "select count(distinct affiliate) from Affiliate affiliate"
    )
    Page<Affiliate> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct affiliate from Affiliate affiliate left join fetch affiliate.user left join fetch affiliate.documentType")
    List<Affiliate> findAllWithToOneRelationships();

    @Query(
        "select affiliate from Affiliate affiliate left join fetch affiliate.user left join fetch affiliate.documentType where affiliate.id =:id"
    )
    Optional<Affiliate> findOneWithToOneRelationships(@Param("id") Long id);
}
