package zerobase.dividend.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
