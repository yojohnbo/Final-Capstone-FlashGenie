package com.techelevator.dao;

import com.techelevator.model.UserDetails;

public interface UserDetailsDao {

    void addEmailToUser(String username, UserDetails userDetails);
}
