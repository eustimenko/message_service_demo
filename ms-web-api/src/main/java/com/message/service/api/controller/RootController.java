package com.message.service.api.controller;

import com.message.service.domain.entity.User;
import com.message.service.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/root")
public class RootController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public List<User> viewAll() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User params) {
        return userService.create(params);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{idUser}")
    public void deleteUser(@PathVariable("idUser") Long idUser) {
        userService.delete(idUser);
    }


    @RequestMapping(method = RequestMethod.GET, value = "role/{idUser}")
    public void makeUserRoot(@PathVariable("idUser") Long idUser) {
        userService.makeUserRoot(idUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "role/{idUser}")
    public void makeUserDefault(@PathVariable("idUser") Long idUser) {
        userService.makeUserDefault(idUser);
    }
}
