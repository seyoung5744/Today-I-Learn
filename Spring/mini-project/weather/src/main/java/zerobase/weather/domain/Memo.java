package zerobase.weather.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    private Long id;
    private String text;

    public Memo(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Memo(String text) {
        this.text = text;
    }
}
