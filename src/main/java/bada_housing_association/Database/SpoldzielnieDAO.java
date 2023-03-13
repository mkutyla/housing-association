package bada_housing_association.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpoldzielnieDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor for this class.
     *
     * @param jdbcTemplate jdbc template
     */
    public SpoldzielnieDAO(JdbcTemplate jdbcTemplate) {
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
    public List<Spoldzielnia> list(boolean asc) throws DataAccessException {
        String sql = "SELECT nr_spoldzielni, nazwa, to_char(data_zalozenia,'DD/MM/YYYY') " +
                "AS data_zalozenia, nr_adresu FROM spoldzielnie";
        if (asc) {
            sql = sql + " ORDER BY nr_spoldzielni";
        } else {
            sql = sql + " ORDER BY nr_spoldzielni DESC";
        }

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Spoldzielnia.class));
    }

    /**
     * Adds a new record.
     *
     * @param spoldzielnia record to be added
     */
    public void add(Spoldzielnia spoldzielnia) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("spoldzielnie")
                .usingColumns("nr_spoldzielni", "nazwa", "data_zalozenia", "nr_adresu");
        spoldzielnia.setNr_spoldzielni(getNextId());
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(spoldzielnia);
        insertActor.execute(param);
    }

    /**
     * Gets a record matching passed unique identifier.
     *
     * @param nr_spoldzielni unique identifier
     * @return matching record
     */
    public Spoldzielnia get(int nr_spoldzielni) {
        Object[] args = {nr_spoldzielni};
        String sql = "SELECT nr_spoldzielni, nazwa, to_char(data_zalozenia,'DD/MM/YYYY')" +
                "AS data_zalozenia, nr_adresu FROM spoldzielnie WHERE nr_spoldzielni = " + args[0];

        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Spoldzielnia.class));
    }

    /**
     * Updates a record by replacing it.
     *
     * @param spoldzielnia record to replace current record
     */
    public void update(Spoldzielnia spoldzielnia) {
        String sql = "UPDATE spoldzielnie SET nazwa=:nazwa, data_zalozenia=TO_DATE(:data_zalozenia, 'DD/MM/YYYY'), " +
                "nr_adresu=:nr_adresu WHERE nr_spoldzielni=:nr_spoldzielni";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(spoldzielnia);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    /**
     * Removes a record matching passed unique identifier.
     *
     * @param nr_spoldzielni unique identifier
     */
    public void delete(int nr_spoldzielni) throws DataAccessException{
        String sql = "DELETE FROM spoldzielnie WHERE nr_spoldzielni = ?";
        jdbcTemplate.update(sql, nr_spoldzielni);
    }


    /**
     * Returns next unique identifier to be assigned to a record.
     */
    private int getNextId() {
        List<Spoldzielnia> spoldzielnie = list(true);
        int id = 1;
        for (Spoldzielnia s : spoldzielnie) {
            if (s.getNr_spoldzielni() == id) id += 1;
            else break;
        }
        return id;
    }
}
