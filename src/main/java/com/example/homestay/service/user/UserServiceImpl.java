package com.example.homestay.service.user;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.reponse.UserResponse;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.dto.request.UserRequest;
import com.example.homestay.dto.request.UserUpdateRequest;
import com.example.homestay.exception.ExistingException;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.UserMapper;
import com.example.homestay.model.User;
import com.example.homestay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.example.homestay.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse get(Integer id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse create(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ExistingException(USER_EXISTED);

        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ExistingException(EMAIL_EXISTED);
        }

        User user = userMapper.toUser(userRequest);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void update(UserUpdateRequest updateRequest, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!updateRequest.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new ExistingException(EMAIL_EXISTED);
            }
        }

            if (!updateRequest.getPhone().equals(existingUser.getPhone())) {
                if (userRepository.existsByPhone(updateRequest.getPhone())) {
                    throw new ExistingException(PHONE_EXISTED);
                }
            }

        userMapper.updateUser(updateRequest, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

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
