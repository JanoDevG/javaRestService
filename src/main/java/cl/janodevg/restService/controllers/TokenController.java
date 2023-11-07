package cl.janodevg.restService.controllers;


import cl.janodevg.restService.utils.JwtUtil;
import cl.janodevg.restService.entities.Response;
import cl.janodevg.restService.entities.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        return ResponseEntity.ok(new Response(JwtUtil.generateToken(user.getName())));
    }
}
