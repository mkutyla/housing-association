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
public class PracownikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor for this class.
     *
     * @param jdbcTemplate jdbc template
     */
    public PracownikDAO(JdbcTemplate jdbcTemplate) {
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
    public List<Pracownik> list(boolean asc) throws DataAccessException {
        String sql = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, to_char(DATA_URODZENIA,'DD/MM/YYYY') as DATA_URODZENIA," +
                " PESEL, PLEC, to_char(DATA_ZATRUDNIENIA,'DD/MM/YYYY') as DATA_ZATRUDNIENIA," +
                " NR_KONTA, WYNAGRODZENIE, EMAIL, NR_TELEFONU, NR_SPOLDZIELNI, NR_ADRESU, NR_STANOWISKA FROM pracownicy";
        if (asc) {
            sql = sql + " ORDER BY NR_PRACOWNIKA";
        } else {
            sql = sql + " ORDER BY NR_PRACOWNIKA DESC";
        }

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Pracownik.class));
    }

    /**
     * Adds a new record.
     *
     * @param pracownik record to be added
     */
    public void add(Pracownik pracownik) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("pracownicy");
        pracownik.setNr_pracownika(getNextId());
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pracownik);
        insertActor.execute(param);
    }

    /**
     * Gets a record matching passed unique identifier.
     *
     * @param nr_pracownika unique identifier
     * @return matching record
     */
    public Pracownik get(int nr_pracownika) {
        Object[] args = {nr_pracownika};
        String sql = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, to_char(DATA_URODZENIA,'DD/MM/YYYY') as DATA_URODZENIA," +
                " PESEL, PLEC, to_char(DATA_ZATRUDNIENIA,'DD/MM/YYYY') as DATA_ZATRUDNIENIA," +
                " NR_KONTA, WYNAGRODZENIE, EMAIL, NR_TELEFONU, NR_SPOLDZIELNI, NR_ADRESU, NR_STANOWISKA FROM pracownicy" +
                " WHERE NR_PRACOWNIKA = " + args[0];

        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Pracownik.class));
    }

    /**
     * Updates a record by replacing it.
     *
     * @param pracownik record to replace current record
     */
    public void update(Pracownik pracownik) {
        String sql = "UPDATE pracownicy SET IMIE=:imie, NAZWISKO=:nazwisko, " +
                "DATA_URODZENIA=TO_DATE(:data_urodzenia, 'DD/MM/YYYY'), PESEL=:pesel, PLEC=:plec, " +
                "DATA_ZATRUDNIENIA=TO_DATE(:data_zatrudnienia,'DD/MM/YYYY'), NR_KONTA=:nr_konta, WYNAGRODZENIE=:wynagrodzenie, " +
                "EMAIL=:email, NR_TELEFONU=:nr_telefonu, NR_SPOLDZIELNI=:nr_spoldzielni, NR_ADRESU=:nr_adresu, NR_STANOWISKA=:nr_stanowiska " +
                "WHERE NR_PRACOWNIKA=:nr_pracownika";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(pracownik);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }

    /**
     * Removes a record matching passed unique identifier.
     *
     * @param nr_pracownika unique identifier
     */
    public void delete(int nr_pracownika) {
        String sql = "DELETE FROM pracownicy WHERE nr_pracownika = ?";
        jdbcTemplate.update(sql, nr_pracownika);
    }


    /**
     * Returns next unique identifier to be assigned to a record.
     */
    private int getNextId() {
        List<Pracownik> pracownicy = list(true);
        int id = 1;
        for (Pracownik p : pracownicy) {
            if (p.getNr_pracownika() == id) id += 1;
            else break;
        }
        return id;
    }
}
