package zerobase.dividend.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.dividend.persist.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
