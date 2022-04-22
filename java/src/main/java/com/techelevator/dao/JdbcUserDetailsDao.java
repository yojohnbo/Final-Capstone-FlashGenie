package com.techelevator.dao;

import com.techelevator.model.UserDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class JdbcUserDetailsDao implements UserDetailsDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addEmailToUser(String username, UserDetails userDetails){
        String sql = "INSERT INTO user_details (user_id, email) " +
                "VALUES ((SELECT users.user_id FROM users WHERE username = ?), ?); ";

        jdbcTemplate.update(sql, username, userDetails.getEmail());
    }

}
