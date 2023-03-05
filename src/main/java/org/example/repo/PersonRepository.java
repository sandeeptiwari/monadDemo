package org.example.repo;

import org.example.Person;
import org.example.config.AppConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    public PersonRepository(AppConfig appConfig) {
        DataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost/mydb", "myuser", "mypassword");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.transactionManager = new JdbcTransactionManager(dataSource);
    }

    public void updatePerson(Person person) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            jdbcTemplate.update("UPDATE Person SET name = ?, age = ? WHERE id = ?",
                    person.getName(), person.getAge(), person.getId());
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
    }

    public Person getPersonById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Person(rs.getLong("id"), rs.getString("name"), rs.getInt("age")));
    }
}
