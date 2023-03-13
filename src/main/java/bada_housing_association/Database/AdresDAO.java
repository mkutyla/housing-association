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
public class AdresDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor for this class.
     *
     * @param jdbcTemplate jdbc template
     */
    public AdresDAO(JdbcTemplate jdbcTemplate) {
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
    public List<Adres> list(boolean asc) throws DataAccessException {
        String sql = "SELECT * FROM adresy";
        if (asc) {
            sql = sql + " ORDER BY nr_adresu";
        } else {
            sql = sql + " ORDER BY nr_adresu DESC";
        }

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Adres.class));
    }

    /**
     * Adds a new record.
     *
     * @param adres record to be added
     */
    public void add(Adres adres) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("adresy")
                .usingColumns("nr_adresu", "ulica", "nr_budynku", "nr_lokalu", "miasto");
        adres.setNr_adresu(getNextId());
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(adres);
        insertActor.execute(param);
    }

    /**
     * Gets a record matching passed unique identifier.
     *
     * @param nr_adresu unique identifier
     * @return matching record
     */
    public Adres get(int nr_adresu) {
        Object[] args = {nr_adresu};
        String sql = "SELECT * FROM adresy WHERE nr_adresu = " + args[0];

        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Adres.class));
    }

    /**
     * Updates a record by replacing it.
     *
     * @param adres record to replace current record
     */
    public void update(Adres adres) {
        String sql = "UPDATE adresy SET ulica=:ulica, nr_budynku=:nr_budynku, " +
                "nr_lokalu=:nr_lokalu, miasto=:miasto WHERE nr_adresu=:nr_adresu";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(adres);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    /**
     * Removes a record matching passed unique identifier.
     *
     * @param nr_adresu unique identifier
     */
    public void delete(int nr_adresu) {
        String sql = "DELETE FROM adresy WHERE nr_adresu = ?";
        jdbcTemplate.update(sql, nr_adresu);
    }

    /**
     * Returns next unique identifier to be assigned to a record.
     */
    private int getNextId() {
        List<Adres> adresy = list(true);
        int id = 1;
        for (Adres a : adresy) {
            if (a.getNr_adresu() == id) id += 1;
            else break;
        }
        return id;
    }
}
