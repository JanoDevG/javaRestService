package cl.janodevg.restService.controllers;

import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UI {

    @Autowired
    private UserService service;

    @GetMapping("/user")
    public ResponseEntity<Response> getUsers(@RequestParam(required = false) String email) {
        if (email == null) {
            return ResponseEntity.ok(new Response(service.findAllUsers()));
        } else {
            return ResponseEntity.ok(new Response(service.findByEmail(email)));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody User user) {
        return new ResponseEntity<>(new Response(service.createdUser(user)), HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody User user, @RequestParam String email) {
        return ResponseEntity.ok(new Response(service.updateUser(user, email)));

    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@RequestParam String email) {
        service.deleteUser(email);
        return ResponseEntity.ok(new Response("usuario eliminado"));
    }
}


