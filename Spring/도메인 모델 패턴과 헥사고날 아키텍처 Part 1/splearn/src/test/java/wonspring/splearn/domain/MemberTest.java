package wonspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("test@test,com", "test", "secret");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}