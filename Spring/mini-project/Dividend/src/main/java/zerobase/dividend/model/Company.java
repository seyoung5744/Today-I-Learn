package zerobase.dividend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.dividend.persist.domain.CompanyEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    private String ticker;
    private String name;

    @Builder
    private Company(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }

    public CompanyEntity toEntity() {
        return CompanyEntity.builder()
            .ticker(this.ticker)
            .name(this.name)
            .build();
    }
}
