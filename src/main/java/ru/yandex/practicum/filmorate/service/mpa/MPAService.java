package ru.yandex.practicum.filmorate.service.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MPAException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
@Slf4j
public class MPAService {
    private final JdbcTemplate jdbcTemplate;

    public MPAService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> getMpa () {
        return jdbcTemplate.query("SELECT * FROM RATES_MPA",
                ((rs, rowNum) -> new Mpa(
                        rs.getInt("mpa_id"),
                        rs.getString("mpa_name"))
                ));
    }

    public Mpa getMpa(int id) {
        SqlRowSet userRows =
                jdbcTemplate.queryForRowSet("SELECT MPA_NAME FROM RATES_MPA WHERE MPA_ID = ?", id);
        if (userRows.next()) {
            Mpa mpa = new Mpa(
                    id,
                    userRows.getString("mpa_name")
            );
            log.info("Ответ mpa = {} ", mpa);
            return mpa;
        } else throw new MPAException("Попытка связаться с отсутствующим id");
    }
}
