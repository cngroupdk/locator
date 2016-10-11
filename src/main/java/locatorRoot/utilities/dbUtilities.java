package locatorRoot.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by cano on 29.9.2016.
 */
public class dbUtilities {

    private dbUtilities(){
    }

    public static JdbcTemplate getJdbcTemplate( String driverClassName, String url, String username, String password){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return new JdbcTemplate(dataSource);
    }

    public static JdbcTemplate getJdbcTemplate( DriverManagerDataSource dataSource ){

        if ( dataSource != null )
            return new JdbcTemplate(dataSource);
        else
           return getDefaultJdbcTemplate();

    }

    public static JdbcTemplate getDefaultJdbcTemplate(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/mydb");
        dataSource.setUsername("cano");
        dataSource.setPassword("PnchMns7722");

        return new JdbcTemplate(dataSource);
    }

    public static SessionFactory getSessionFactory(){

        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        return sf;
    }

}
