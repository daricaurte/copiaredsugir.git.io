package co.edu.sena.repository;

import co.edu.sena.domain.Networks;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Networks entity.
 */
@Repository
public interface NetworksRepository extends JpaRepository<Networks, Long> {
    default Optional<Networks> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Networks> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Networks> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct networks from Networks networks left join fetch networks.contactData",
        countQuery = "select count(distinct networks) from Networks networks"
    )
    Page<Networks> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct networks from Networks networks left join fetch networks.contactData")
    List<Networks> findAllWithToOneRelationships();

    @Query("select networks from Networks networks left join fetch networks.contactData where networks.id =:id")
    Optional<Networks> findOneWithToOneRelationships(@Param("id") Long id);
}
