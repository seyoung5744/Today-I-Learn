package wonspring.splearn;

import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;
import wonspring.splearn.domain.member.MemberRegisterRequest;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AsserThatUtils {

    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> assertThat(value).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return value -> assertThat(value).isEqualTo(request.email());
    }
}
