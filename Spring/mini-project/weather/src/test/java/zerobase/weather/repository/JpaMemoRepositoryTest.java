package zerobase.weather.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class JpaMemoRepositoryTest {


    @Autowired
    private JpaMemoRepository jpaMemoRepository;

    @Test
    void save() {
        // given
        Memo memo = new Memo("test");

        // when
        Memo savedMemo = jpaMemoRepository.save(memo);

        // then
        assertThat(savedMemo.getId()).isNotNull();
        assertThat(savedMemo.getText()).isEqualTo("test");
    }

    @Test
    void findById() {
        // given
        Memo memo = new Memo("test");
        Memo savedMemo = jpaMemoRepository.save(memo);

        // when
        Optional<Memo> optionalMemo = jpaMemoRepository.findById(savedMemo.getId());

        // then
        assertThat(optionalMemo).isPresent();
        assertThat(optionalMemo.get().getId()).isNotNull();
        assertThat(optionalMemo.get().getText()).isEqualTo("test");
    }
}