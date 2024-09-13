package zerobase.weather.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

@Repository
public class JdbcMemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "insert into memo(text) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, memo.getText());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        memo.setId(key);
        return memo;
    }

    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(Long id) {
        String sql = "select * from memo where id = ?";

        try {
            Memo memo = jdbcTemplate.queryForObject(sql, memoRowMapper(), id);
            return Optional.of(memo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Memo> memoRowMapper() {
        // ResultSet
        // {id = 1, text = 'this is memo~'}
        return (rs, rowNum) -> new Memo(
            rs.getLong("id"),
            rs.getString("text")
        );
    }
}
