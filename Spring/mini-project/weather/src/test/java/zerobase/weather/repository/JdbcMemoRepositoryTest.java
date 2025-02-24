package zerobase.weather.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class JdbcMemoRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcMemoRepository jdbcMemoRepository;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "create table memo ("
            + "  id bigint generated by default as identity,"
            + "  text varchar(50) not null,"
            + "  primary key (id)"
            + ");";
        jdbcTemplate.update(sql);
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "drop table memo";
        jdbcTemplate.update(sql);
    }

    @DisplayName("메모 저장")
    @Test
    void save() {
        // given
        Memo memo = new Memo("test");

        // when
        Memo savedMemo = jdbcMemoRepository.save(memo);

        // then
        assertThat(savedMemo.getId()).isNotNull();
        assertThat(savedMemo.getText()).isEqualTo("test");
    }

    @DisplayName("메모를 저장하면 Id로 단일 조회가 가능하다.")
    @Test
    void findById() {
        // given
        Long id = 1L;
        Memo memo = new Memo("test");
        jdbcMemoRepository.save(memo);

        // when
        Optional<Memo> optionalMemo = jdbcMemoRepository.findById(id);

        // then
        assertThat(optionalMemo).isPresent();
        assertThat(optionalMemo.get().getId()).isEqualTo(id);
        assertThat(optionalMemo.get().getText()).isEqualTo("test");
    }

    @DisplayName("모든 메모 조회")
    @Test
    void findAll() {
        // given
        Memo memo1 = new Memo("test1");
        Memo memo2 = new Memo("test2");
        Memo memo3 = new Memo("test3");
        jdbcMemoRepository.save(memo1);
        jdbcMemoRepository.save(memo2);
        jdbcMemoRepository.save(memo3);

        // when
        List<Memo> memos = jdbcMemoRepository.findAll();

        // then
        assertThat(memos).hasSize(3)
            .extracting("text")
            .containsExactlyInAnyOrder("test1", "test2", "test3");
    }
}