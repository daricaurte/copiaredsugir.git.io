package co.edu.sena.repository;

import co.edu.sena.domain.PriorityOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PriorityOrder entity.
 */
@Repository
public interface PriorityOrderRepository extends JpaRepository<PriorityOrder, Long> {
    default Optional<PriorityOrder> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PriorityOrder> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PriorityOrder> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct priorityOrder from PriorityOrder priorityOrder left join fetch priorityOrder.contactData",
        countQuery = "select count(distinct priorityOrder) from PriorityOrder priorityOrder"
    )
    Page<PriorityOrder> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct priorityOrder from PriorityOrder priorityOrder left join fetch priorityOrder.contactData")
    List<PriorityOrder> findAllWithToOneRelationships();

    @Query("select priorityOrder from PriorityOrder priorityOrder left join fetch priorityOrder.contactData where priorityOrder.id =:id")
    Optional<PriorityOrder> findOneWithToOneRelationships(@Param("id") Long id);
}
