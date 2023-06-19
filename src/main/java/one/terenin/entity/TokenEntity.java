package one.terenin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import one.terenin.dto.common.UserRole;
import one.terenin.entity.parent.AbstractEntity;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token_table")
public class TokenEntity extends AbstractEntity {
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "username")
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;
    @Column(name = "token")
    private String token;

}
