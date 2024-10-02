package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;
import com.example.homestay.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityUtil.isUserOwner(authentication.name, #id)")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable Integer id) {
        return ResponseEntity.ok(
                new ApiResponse<>("User fetched successfully", userService.get(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<UserResponse>>> list(
            @RequestParam Integer page,
            @RequestParam Integer size) {

        PagingRequest pageRequest = new PagingRequest(page, size);
        return ResponseEntity.ok(
                new ApiResponse<>("User list fetched successfully", userService.list(pageRequest))
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(
                new ApiResponse<>("User created successfully", userService.create(request)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody UserUpdateRequest updateRequest,
                                                 @PathVariable Integer id) {
        userService.update(updateRequest, id);
        return ResponseEntity.ok(
                new ApiResponse<>("User updated successfully"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>("User deleted successfully"));
    }

}
