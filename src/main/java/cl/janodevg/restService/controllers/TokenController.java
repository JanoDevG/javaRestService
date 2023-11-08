package cl.janodevg.restService.controllers;


import cl.janodevg.restService.common.validations.BasicAuth;
import cl.janodevg.restService.utils.JwtUtil;
import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenController {

    @Autowired
    private BasicAuth basicAuth;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid User user) throws AccessDeniedException {
        basicAuth.filterRequest();
        return ResponseEntity.ok(new Response(JwtUtil.generateToken(user.getName())));
    }
}
