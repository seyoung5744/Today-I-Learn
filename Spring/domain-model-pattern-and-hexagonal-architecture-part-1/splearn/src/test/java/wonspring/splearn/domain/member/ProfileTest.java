package wonspring.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile() {
        new Profile("won");
        new Profile("won1235");
        new Profile("12345");
        new Profile("");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("tooLongtooLongtooLongtooLong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한글")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("won");

        assertThat(profile.url()).isEqualTo("@won");
    }
}