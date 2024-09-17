package zerobase.dividend.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.dividend.persist.domain.DividendEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dividend {

    private LocalDateTime date;
    private String dividend;

    @Builder
    private Dividend(LocalDateTime date, String dividend) {
        this.date = date;
        this.dividend = dividend;
    }

    public static Dividend of(DividendEntity dividendEntity) {
        return Dividend.builder()
            .date(dividendEntity.getDate())
            .dividend(dividendEntity.getDividend())
            .build();
    }

    public DividendEntity toEntity(Long companyId) {
        return DividendEntity.builder()
            .companyId(companyId)
            .date(this.date)
            .dividend(this.dividend)
            .build();
    }
}
