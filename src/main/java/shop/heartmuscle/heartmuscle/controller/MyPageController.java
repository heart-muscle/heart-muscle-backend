package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.heartmuscle.heartmuscle.dto.UserDto;
import shop.heartmuscle.heartmuscle.dto.response.UserResponseDto;
import shop.heartmuscle.heartmuscle.entity.User;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.service.FeedServiceImpl;
import shop.heartmuscle.heartmuscle.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    // 전체 유저 불러오기
    @Operation(description = "전체 유저 불러오기", method = "GET")
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    // 유저 정보 불러오기( id = username )
    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    @GetMapping("/user/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponseDto response = modelMapper.map(user, new TypeToken<UserResponseDto>() {}.getType());
        return response;
    }

    // 유저 프로필 수정하기
    @Operation(description = "유저 프로필 수정하기", method = "POST")
    @PostMapping("/user/detail")
    public String updateUser(UserDto userDto) throws IOException {
        userService.update(userDto);
        return "완료!";
    }

}
