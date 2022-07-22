package backend.server.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import backend.server.entity.User;
import backend.server.repository.IUserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepo.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in getAllUsers: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/register")
    public void register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(user);
        try {
            User usr = userRepo.findByEmail(user.getEmail());
            if (usr != null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "User with this email already exists");
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("Registering user: " + user);
            userRepo.save(user);
            Map<String, Object> success = new HashMap<>();
            success.put("user", user);
            response.setStatus(HttpStatus.CREATED.value());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), success);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    @PostMapping("/token_refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String refresh_token = request.getParameter("refresh_token");
            if (refresh_token != null ) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT jwt = verifier.verify(refresh_token);
                    String email = jwt.getSubject();

                    User user = userRepo.findByEmail(email);
                    String access_token = JWT.create()
                            .withSubject(user.getEmail())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*10000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("role", user.getRole().toString())
                            .withClaim("id", user.getId())
                            .sign(algorithm);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);


                } catch (Exception e) {
                    response.setStatus(401);
                    Map<String, String> error = new HashMap<>();
                    error.put("error", e.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }
            else {
                throw new RuntimeException("Refresh token is missing");
            }
        } catch (Exception e) {
                response.setStatus(401);
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

}
