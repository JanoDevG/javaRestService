package cl.janodevg.restService.controllers;

import cl.janodevg.restService.services.UserService;
import cl.janodevg.restService.utils.JwtUtil;
import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.common.exceptions.UnauthorizedException;
import cl.janodevg.restService.common.validations.EmailValidationAnnotation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UI {

    @Autowired
    @SuppressWarnings("unused")
    private UserService service;

    @GetMapping("/user")
    public ResponseEntity<Response> getUsers(@RequestParam  String email,
                                             @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && JwtUtil.validateToken(token)) {
            if (email == null) {
                return ResponseEntity.ok(new Response(service.findAllUsers()));
            } else {
                return ResponseEntity.ok(new Response(service.findUserByEmail(email)));
            }
        } else {
            throw new UnauthorizedException("JWT isn't valid or missing.");
        }


    }

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody @Valid User user,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && JwtUtil.validateToken(token)) {
            return new ResponseEntity<>(new Response(service.createUser(user)), HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException("JWT isn't valid or missing.");
        }

    }

    @PatchMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid User user, @EmailValidationAnnotation @RequestParam String  email,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && JwtUtil.validateToken(token)) {
            return ResponseEntity.ok(new Response(service.updateUser(user, email)));
        } else {
            throw new UnauthorizedException("JWT isn't valid or missing.");
        }

    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@EmailValidationAnnotation @RequestParam String email,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && JwtUtil.validateToken(token)) {
            service.deleteUser(email);
            return ResponseEntity.ok(new Response("user now is inactive."));
        } else {
            throw new UnauthorizedException("JWT isn't valid or missing.");
        }
    }
}


