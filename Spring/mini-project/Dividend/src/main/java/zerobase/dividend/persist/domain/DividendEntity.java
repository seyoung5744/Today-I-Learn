package zerobase.dividend.persist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "DIVIDEND")
@Table(uniqueConstraints = {
    @UniqueConstraint( // 복합 Unique 키 설정
        columnNames = {"companyId", "date"}
    )
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;

    @Builder
    private DividendEntity(Long companyId, LocalDateTime date, String dividend) {
        this.companyId = companyId;
        this.date = date;
        this.dividend = dividend;
    }
}
