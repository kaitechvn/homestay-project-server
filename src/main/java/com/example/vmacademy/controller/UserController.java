package com.example.vmacademy.controller;

import com.example.vmacademy.dto.ApiResponse;
import com.example.vmacademy.dto.reponse.PagingResponse;
import com.example.vmacademy.dto.reponse.UserResponse;
import com.example.vmacademy.dto.request.PagingRequest;
import com.example.vmacademy.dto.request.UserRequest;
import com.example.vmacademy.dto.request.UserUpdateRequest;
import com.example.vmacademy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable Integer id) {
        return ResponseEntity.ok(
                new ApiResponse<>("Get success", userService.get(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<UserResponse>>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        PagingRequest pageRequest = new PagingRequest(page, size);
        return ResponseEntity.ok(
                new ApiResponse<>("List success", userService.list(pageRequest))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(
                new ApiResponse<>("Create success", userService.create(request)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody UserUpdateRequest updateRequest,
                                                 @PathVariable Integer id) {
        userService.update(updateRequest, id);
        return ResponseEntity.ok(
                new ApiResponse<>("Update success"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> partialUpdate(@RequestBody UserUpdateRequest updateRequest,
                                                           @PathVariable Integer id) {
        userService.partialUpdate(updateRequest, id);
        return ResponseEntity.ok(
                new ApiResponse<>("Partial update success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Delete success"));
    }
}
