package com.optic.apirest.controllers;

import com.optic.apirest.dto.user.*;
import com.optic.apirest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET - OBTENER
    // POST - CREAR
    // PUT - ACTUALIZAR
    // DELETE - ELIMINAR

//    @PostMapping
//    public ResponseEntity<UserResponse> create (@RequestBody CreateUserRequest request) {
//        UserResponse user = userService.create(request);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        try {
            UserResponse response = userService.findById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        }
    }

    @PutMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update (@PathVariable Long id, @ModelAttribute UpdateUserRequest request) {
        try {
            UserResponse response = userService.updateUserWithImage(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value()
            ));
        }
    }

}
