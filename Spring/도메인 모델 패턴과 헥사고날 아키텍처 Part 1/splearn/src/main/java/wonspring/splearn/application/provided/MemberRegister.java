package wonspring.splearn.application.provided;

import wonspring.splearn.domain.Member;
import wonspring.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
public interface MemberRegister {

    Member register(MemberRegisterRequest registerRequest);
}
