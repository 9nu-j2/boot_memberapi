package hello.hello_spring.service;

import hello.hello_spring.dto.MemberDTO;
import hello.hello_spring.entity.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원가입
     */

    private void validateDuplicateMember(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if(memberOptional.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public MemberDTO createMember(String email, String password) {
        validateDuplicateMember(email);

        Long id = Long.valueOf(memberRepository.count() + 1);
        Member user = new Member();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        memberRepository.save(user);
        return new MemberDTO(id, email);
    }

    /**
     * 회원 조회
     */
    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(user -> new MemberDTO(user.getId(), user.getEmail()));
    }


    /**
     * 전체 회원 조회
     */
    public List<MemberDTO> findMembers() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberDTO(member.getId(), member.getEmail()))
                .collect(Collectors.toList());
    }



    /**
     * 회원 탈퇴
     */
    public boolean deleteMember(Long seq) {
        Optional<Member> memberOptional = memberRepository.findById(seq);
        if (memberOptional.isPresent()) {
            memberRepository.deleteById(seq);
            return true;
        } else {
            return false;
        }
    }
}
