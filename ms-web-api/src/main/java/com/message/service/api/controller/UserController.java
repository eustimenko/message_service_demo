package com.message.service.api.controller;

import com.message.service.domain.entity.User;
import com.message.service.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "{idUser}")
    public List<User> viewFriends(@PathVariable("idUser") Long idUser) {
        return userService.findById(idUser).getFriendsStream().collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "book/{idUser}")
    public List<User> viewPossibleFriends(@PathVariable("idUser") Long idUser) {
        return userService.getPossibleFriends(idUser);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{idUser}/{idFriend}")
    public void makeFriend(@PathVariable("idUser") Long idUser, @PathVariable("idFriend") Long idFriend) {
        userService.makeFriend(idUser, idFriend);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{idUser}/{idFriend}")
    public void deleteFriend(@PathVariable("idUser") Long idUser, @PathVariable("idFriend") Long idFriend) {
        userService.deleteFriend(idUser, idFriend);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{idUser}")
    public void resetPassword(@PathVariable("idUser") Long idUser, @RequestParam(value = "password") String password) {
        userService.resetPassword(idUser, password);
    }

}
