package bada_housing_association.Database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SpoldzielnieDAOTest {

    private SpoldzielnieDAO dao;

    @BeforeEach
    void setUp() throws Exception{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:oracle:thin:@194.29.170.4:1521:xe");
        dataSource.setUsername("BADAGRB06");
        dataSource.setPassword("BADAGRB06");
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");

        /* Import JdbcTemplate */
        dao = new SpoldzielnieDAO(new JdbcTemplate(dataSource));
    }

    @Test
    void testList(){
        List<Spoldzielnia> listSpoldzielnia = dao.list(true);

        assertTrue(!listSpoldzielnia.isEmpty());
    }

    @Test
    void testSave(){
        fail("Not yet implemented");
    }

    @Test
    void testGet(){
        fail("Not yet implemented");
    }

    @Test
    void testUpdate(){
        fail("Not yet implemented");
    }

    @Test
    void testDelete(){
        fail("Not yet implemented");
    }
}
