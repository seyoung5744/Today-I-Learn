package zerobase.dividend.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.dividend.persist.domain.Dividend;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

}