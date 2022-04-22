package com.techelevator.controller;

import com.techelevator.dao.UserDetailsDao;
import com.techelevator.model.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class UserDetailsController {
    private UserDetailsDao userDetailsDao;

    public UserDetailsController (UserDetailsDao userDetailsDao){
        this.userDetailsDao = userDetailsDao;
    }


    @RequestMapping(value = "/{username}/email", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmail(@PathVariable String username, @RequestBody @Valid UserDetails userDetails){
        userDetailsDao.addEmailToUser(username, userDetails);
    }
}
