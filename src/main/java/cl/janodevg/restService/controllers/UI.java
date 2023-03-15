package cl.janodevg.restService.controllers;

import cl.janodevg.restService.configurations.JwtUtil;
import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.services.UserService;
import cl.janodevg.restService.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UI {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user")
    public ResponseEntity<Response> getUsers(@RequestParam(required = false) String email,
                                             @RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if (email == null) {
                return ResponseEntity.ok(new Response(service.findAllUsers()));
            } else {
                return ResponseEntity.ok(new Response(service.findByEmail(email)));
            }
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }


    }

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody User user,
                                               @RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            return new ResponseEntity<>(new Response(service.createdUser(user)), HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }

    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody User user, @RequestParam String email,
                                               @RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok(new Response(service.updateUser(user, email)));
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }

    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@RequestParam String email,
                                               @RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            service.deleteUser(email);
            return ResponseEntity.ok(new Response("usuario eliminado"));
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }
    }
}


