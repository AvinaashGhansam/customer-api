package com.ag.customer.service;

import com.ag.customer.Customer;
import com.ag.customer.repository.CustomerDao;
import com.ag.customer.repository.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        String sql = """
                SELECT * FROM customer;
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        String sql = """
                SELECT id, name, email, age FROM customer WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql = """
                INSERT INTO customer (name, email, age)
                VALUES(?, ?, ?);
                """;
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        String sql = """
        SELECT count(id) FROM customer WHERE email = ?;
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        String sql = """
                DELETE FROM customer WHERE id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsPersonById(Integer id) {
        // TODO: Fix Method Name, not consistent
        String sql = """
        SELECT count(id) FROM customer WHERE id = ?;
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        if (updatedCustomer.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?;";
            jdbcTemplate.update(sql, updatedCustomer.getName(), updatedCustomer.getId());
        }

        if (updatedCustomer.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?;";
            jdbcTemplate.update(sql, updatedCustomer.getEmail(), updatedCustomer.getId());
        }

        if (updatedCustomer.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?;";
            jdbcTemplate.update(sql, updatedCustomer.getAge(), updatedCustomer.getId());
        }
    }
}
