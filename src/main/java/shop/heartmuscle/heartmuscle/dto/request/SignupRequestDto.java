package shop.heartmuscle.heartmuscle.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}