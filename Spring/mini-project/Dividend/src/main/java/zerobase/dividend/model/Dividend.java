package zerobase.dividend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
