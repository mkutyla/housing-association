package bada_housing_association.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StanowiskoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor for this class.
     *
     * @param jdbcTemplate jdbc template
     */
    public StanowiskoDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setQueryTimeout(5);
    }

    /**
     * Gets all the records sorted by unique identifier.
     *
     * @param asc if True, records are in ascending order, in descending if False
     * @return all the records
     * @throws DataAccessException if connection with database is compromised
     */
    public List<Stanowisko> list(boolean asc) throws DataAccessException {
        String sql = "SELECT * FROM stanowiska";
        if (asc) {
            sql = sql + " ORDER BY NR_STANOWISKA";
        } else {
            sql = sql + " ORDER BY NR_STANOWISKA DESC";
        }

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Stanowisko.class));
    }

    /**
     * Adds a new record.
     *
     * @param stanowisko record to be added
     */
    public void add(Stanowisko stanowisko) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("stanowiska");
        stanowisko.setNr_stanowiska(getNextId());
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(stanowisko);
        insertActor.execute(param);
    }

    /**
     * Gets a record matching passed unique identifier.
     *
     * @param nr_stanowiska unique identifier
     * @return matching record
     */
    public Stanowisko get(int nr_stanowiska) {
        Object[] args = {nr_stanowiska};
        String sql = "SELECT * FROM stanowiska WHERE nr_stanowiska = " + args[0];

        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Stanowisko.class));
    }

    /**
     * Updates a record by replacing it.
     *
     * @param stanowisko record to replace current record
     */
    public void update(Stanowisko stanowisko) {
        String sql = "UPDATE stanowiska SET NAZWA_STANOWISKA=:nazwa_stanowiska, OPIS=:opis WHERE NR_STANOWISKA=:nr_stanowiska";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(stanowisko);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    /**
     * Removes a record matching passed unique identifier.
     *
     * @param nr_stanowiska unique identifier
     */
    public void delete(int nr_stanowiska) throws DataAccessException {
        String sql = "DELETE FROM stanowiska WHERE NR_STANOWISKA = ?";
        jdbcTemplate.update(sql, nr_stanowiska);
    }

    /**
     * Returns next unique identifier to be assigned to a record.
     */
    private int getNextId() {
        List<Stanowisko> stanowiska = list(true);
        int id = 1;
        for (Stanowisko s : stanowiska) {
            if (s.getNr_stanowiska() == id) id += 1;
            else break;
        }
        return id;
    }
}
