package co.edu.sena.repository;

import co.edu.sena.domain.ContactData;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactData entity.
 */
@Repository
public interface ContactDataRepository extends JpaRepository<ContactData, Long> {
    default Optional<ContactData> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContactData> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContactData> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct contactData from ContactData contactData left join fetch contactData.affiliate",
        countQuery = "select count(distinct contactData) from ContactData contactData"
    )
    Page<ContactData> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct contactData from ContactData contactData left join fetch contactData.affiliate")
    List<ContactData> findAllWithToOneRelationships();

    @Query("select contactData from ContactData contactData left join fetch contactData.affiliate where contactData.id =:id")
    Optional<ContactData> findOneWithToOneRelationships(@Param("id") Long id);
}
