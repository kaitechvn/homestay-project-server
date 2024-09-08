package com.example.vmacademy.service;
import com.example.vmacademy.dto.reponse.PagingResponse;
import com.example.vmacademy.dto.reponse.UserResponse;
import com.example.vmacademy.dto.request.PagingRequest;
import com.example.vmacademy.dto.request.UserRequest;
import com.example.vmacademy.dto.request.UserUpdateRequest;
import com.example.vmacademy.exception.ExistingException;
import com.example.vmacademy.exception.NotFoundException;
import com.example.vmacademy.mapper.UserMapper;
import com.example.vmacademy.model.User;
import com.example.vmacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.vmacademy.utils.Constants.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse get(Integer id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "User Resource Error"));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        // Check if the username or email already exists
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ExistingException(USER_EXISTED, "User Resource Error");

        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ExistingException(EMAIL_EXISTED, "Email Resource Error");
        }

        // Map the UserRequest DTO to the User entity
        User user = userMapper.toUser(userRequest);

        // Save the new user
        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void update(UserUpdateRequest updateRequest, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "Resource Error"));

        if (updateRequest.getEmail() != null && userRepository.existsByEmail(updateRequest.getEmail())) {
            throw new ExistingException(EMAIL_EXISTED, "Resource Error");
        }

        userMapper.fullUpdateUser(updateRequest, existingUser);

        userRepository.save(existingUser);

    }

    @Override
    public void partialUpdate(UserUpdateRequest updateRequest, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "Resource Error"));

        if (updateRequest.getEmail() != null && userRepository.existsByEmail(updateRequest.getEmail())) {
            throw new ExistingException(EMAIL_EXISTED, "Resource Error");
        }

        userMapper.partialUpdateUser(updateRequest, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, "Resource Error"));

        userRepository.deleteById(id);

    }

    @Override
    public PagingResponse<UserResponse> list(PagingRequest prequest) {
        Pageable page = PageRequest.of(prequest.getPage() - 1, prequest.getSize());

        Page<User> pageResult = userRepository.findAll(page);

        return PagingResponse.from(
                prequest.getPage(),
                prequest.getSize(),
                pageResult.getTotalElements(),
                pageResult.stream()
                           .map(userMapper::toUserResponse)
                           .toList());
    }

}
