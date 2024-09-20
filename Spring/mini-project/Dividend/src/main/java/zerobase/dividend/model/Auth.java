package zerobase.dividend.model;

import java.util.List;
import lombok.Data;
import zerobase.dividend.persist.domain.MemberEntity;

public class Auth {

    @Data
    public static class SignIn {

        private String username;
        private String password;
    }

    @Data
    public static class SingUp {

        private String username;
        private String password;
        private List<String> roles;

        public void encodePassword(String password) {
            this.password = password;
        }

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();
        }
    }
}
