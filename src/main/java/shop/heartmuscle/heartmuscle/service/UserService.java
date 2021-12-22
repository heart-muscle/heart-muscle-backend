package shop.heartmuscle.heartmuscle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.domain.UserRole;
import shop.heartmuscle.heartmuscle.domain.WorkoutTag;
import shop.heartmuscle.heartmuscle.dto.FeedRequestDto;
import shop.heartmuscle.heartmuscle.dto.SignupRequestDto;
import shop.heartmuscle.heartmuscle.dto.UserDto;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;
import shop.heartmuscle.heartmuscle.security.kakao.KakaoOAuth2;
import shop.heartmuscle.heartmuscle.security.kakao.KakaoUserInfo;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final KakaoOAuth2 kakaoOAuth2;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final AwsService awsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2, AuthenticationManager authenticationManager, AwsService awsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
        this.authenticationManager = authenticationManager;
        // 이거 추가하는 이유 하림님 질문하기
        this.awsService = awsService;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        String nickname = username;
        String imgUrl = "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png";

        User user = new User(username, password, email, role, nickname, imgUrl);
        userRepository.save(user);
    }

    public String kakaoLogin(String token) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        System.out.println("카카오로그인경중경중");
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;

            String imgUrl = "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png";
            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId, imgUrl);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("리턴되기직전경중경중");
        return username;
    }

    public int checkSignup(String id) {
        Optional<User> found = userRepository.findByUsername(id);
        if (found.isPresent()) {
            return 0;
        } else {
            return 1;
        }
    }

    public User getUser(String id) {

        return userRepository.findByUsername(id).orElseThrow(
                () -> new NullPointerException("존재하지않습니다.")
        );
    }

    @Transactional
    public Long update(UserDto userDto) throws IOException {

        String url = null;

        if(userDto.getImgUrl() != null) url = awsService.upload(userDto.getImgUrl());
        else url = "https://teamco-bucket.s3.ap-northeast-2.amazonaws.com/Profile-PNG-Clipart.png";

        String id = userDto.getUsername();

        User user = userRepository.findByUsername(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );

        user.update(userDto, url);
        return user.getId();
    }

    public Optional<User> getProfile(UserDetailsImpl userDetails) {
        String user = userDetails.getUsername();
        return userRepository.findByUsername(user);
    }
}