package com.example.apisecurity.repository;

import com.example.apisecurity.entity.JdbcCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerDangerousRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcCustomer findCustomerByEmail(String email) {
        String sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer "
                + "WHERE email = '" + email + "' AND email IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, this::mapToCustomer);
    }

    public List<JdbcCustomer> findCustomersByGender(String genderCode) {
        String sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer "
                + "WHERE gender = '" + genderCode + "'";

        return jdbcTemplate.query(sql, this::mapToCustomer);
    }

    public void createCustomer(JdbcCustomer newCustomer) {
        String sql = "INSERT INTO jdbc_customer(full_name, email, birth_date, gender) VALUES ('"
                + newCustomer.getFullName() + "','"
                + newCustomer.getEmail() + "','"
                + newCustomer.getBirthDate() + "','"
                + newCustomer.getGender() + "')";
        jdbcTemplate.execute(sql);
    }

    private JdbcCustomer mapToCustomer(ResultSet rs, long rowNum) throws SQLException {
        JdbcCustomer customer = new JdbcCustomer();

        Optional.ofNullable(rs.getDate("birth_date")).ifPresent(b -> customer.setBirthDate(b.toLocalDate()));
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setEmail(rs.getString("email"));
        customer.setFullName(rs.getString("full_name"));
        customer.setGender(rs.getString("gender"));

        return customer;
    }
}
