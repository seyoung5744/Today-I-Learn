package zerobase.dividend.persist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "COMPANY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

    @Builder
    private CompanyEntity(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }
}
