package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.supports.IO;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

/**
 * Hello world!
 *
 */
public class App 
{





    public static void main( String[] args )
    {
        DataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost/mydb", "myuser", "mypassword");

        Injector injector = Guice.createInjector(new AppModule());


        // Get an instance of MyService from the injector
        AppService service = injector.getInstance(AppService.class);
    }
}
