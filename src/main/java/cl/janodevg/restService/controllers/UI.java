package cl.janodevg.restService.controllers;

import cl.janodevg.restService.services.IUserService;
import cl.janodevg.restService.utils.JwtUtil;
import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.services.exceptions.UnauthorizedException;
import cl.janodevg.restService.services.validations.EmailValidationAnnotation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class UI {

    @Autowired
    @SuppressWarnings("unused")
    private IUserService service;

    @GetMapping("/user")
    public ResponseEntity<Response> getUsers(@RequestParam  String email,
                                             @RequestHeader("Authorization") String token) {
        if (JwtUtil.validateToken(token)) {
            if (email == null) {
                return ResponseEntity.ok(new Response(service.findAllUsers()));
            } else {
                return ResponseEntity.ok(new Response(service.findUserByEmail(email)));
            }
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }


    }

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody @Valid User user,
                                               @RequestHeader("Authorization") String token) {
        if (JwtUtil.validateToken(token)) {
            return new ResponseEntity<>(new Response(service.createUser(user)), HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }

    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid User user, @EmailValidationAnnotation @RequestParam String  email,
                                               @RequestHeader("Authorization") String token) {
        if (JwtUtil.validateToken(token)) {
            return ResponseEntity.ok(new Response(service.updateUser(user, email)));
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }

    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@EmailValidationAnnotation @RequestParam String email,
                                               @RequestHeader("Authorization") String token) {
        if (JwtUtil.validateToken(token)) {
            service.deleteUser(email);
            return ResponseEntity.ok(new Response("usuario eliminado"));
        } else {
            throw new UnauthorizedException("JWT no válido.");
        }
    }
}


