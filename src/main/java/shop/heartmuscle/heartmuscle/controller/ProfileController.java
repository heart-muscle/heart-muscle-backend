package shop.heartmuscle.heartmuscle.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.UserDto;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {


    private final UserRepository userRepository;
    private UserService userService;

    // 전체 유저 불러오기
    @Operation(description = "전체 유저 불러오기", method = "GET")
    @GetMapping("/user")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 유저 한명 불러오기
    @Operation(description = "특정 유저 정보 불러오기", method = "GET")
    @GetMapping("/user/{id}")
    public User getFeed(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 한명 불러오기
    @Operation(description = "특정 유저 정보 불러오기 / 프로필수정", method = "GET")
    @GetMapping("/user/detail/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    // 유저 프로필 수정하기
    @Operation(description = "유저 프로필 수정하기", method = "POST")
    @PostMapping("/user/detail")
    public String updateUser(UserDto userDto) throws IOException {
        System.out.println("수정할 피드 아이디" + userDto.getUsername());
        System.out.println("수정할 피드 이미지" + userDto.getImgUrl());
        System.out.println("수정할 피드 이미지" + userDto.getNickname());
        userService.update(userDto);
        return "완료!";
    }

}
