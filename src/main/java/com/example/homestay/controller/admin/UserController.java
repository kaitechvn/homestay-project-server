package com.example.homestay.controller.admin;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;
import com.example.homestay.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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
                new ApiResponse<>("List successfully", userService.list(pageRequest))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(
                new ApiResponse<>("Created successfully", userService.create(request)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody UserUpdateRequest updateRequest,
                                                 @PathVariable Integer id) {
        userService.update(updateRequest, id);
        return ResponseEntity.ok(
                new ApiResponse<>("Updated successfully"));
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<ApiResponse<Void>> partialUpdate(@RequestBody UserUpdateRequest updateRequest,
//                                                           @PathVariable Integer id) {
//        userService.partialUpdate(updateRequest, id);
//        return ResponseEntity.ok(
//                new ApiResponse<>("Partial update success"));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Deleted successfully"));
    }
}
