package one.terenin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TokenResponse {
    private String token;
    private UUID userId;
    private String username;
    private String role;
}
