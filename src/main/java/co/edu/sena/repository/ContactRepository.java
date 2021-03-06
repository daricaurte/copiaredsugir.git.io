package co.edu.sena.repository;

import co.edu.sena.domain.Contact;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Contact entity.
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    default Optional<Contact> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Contact> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Contact> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct contact from Contact contact left join fetch contact.contactData",
        countQuery = "select count(distinct contact) from Contact contact"
    )
    Page<Contact> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct contact from Contact contact left join fetch contact.contactData")
    List<Contact> findAllWithToOneRelationships();

    @Query("select contact from Contact contact left join fetch contact.contactData where contact.id =:id")
    Optional<Contact> findOneWithToOneRelationships(@Param("id") Long id);
}
