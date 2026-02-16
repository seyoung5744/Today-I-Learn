package wonspring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wonspring.splearn.application.provided.MemberRegister;
import wonspring.splearn.application.required.EmailSender;
import wonspring.splearn.application.required.MemberRepository;
import wonspring.splearn.domain.Member;
import wonspring.splearn.domain.MemberRegisterRequest;
import wonspring.splearn.domain.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");

        return member;
    }
}
