package co.edu.sena.repository;

import co.edu.sena.domain.Relation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Relation entity.
 */
@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    default Optional<Relation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Relation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Relation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct relation from Relation relation left join fetch relation.contactData",
        countQuery = "select count(distinct relation) from Relation relation"
    )
    Page<Relation> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct relation from Relation relation left join fetch relation.contactData")
    List<Relation> findAllWithToOneRelationships();

    @Query("select relation from Relation relation left join fetch relation.contactData where relation.id =:id")
    Optional<Relation> findOneWithToOneRelationships(@Param("id") Long id);
}
