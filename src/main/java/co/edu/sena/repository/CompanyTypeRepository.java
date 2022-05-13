package co.edu.sena.repository;

import co.edu.sena.domain.CompanyType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyType entity.
 */
@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
    default Optional<CompanyType> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CompanyType> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CompanyType> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct companyType from CompanyType companyType left join fetch companyType.contactData",
        countQuery = "select count(distinct companyType) from CompanyType companyType"
    )
    Page<CompanyType> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct companyType from CompanyType companyType left join fetch companyType.contactData")
    List<CompanyType> findAllWithToOneRelationships();

    @Query("select companyType from CompanyType companyType left join fetch companyType.contactData where companyType.id =:id")
    Optional<CompanyType> findOneWithToOneRelationships(@Param("id") Long id);
}
