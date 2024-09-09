package hello.hello_spring.service;

import hello.hello_spring.dto.MemberDTO;
import hello.hello_spring.dto.MemberLoginDTO;
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
     * 로그인
     */

    public Member login(String ownername, String password) {
//        Optional<Member> memberOptional = memberRepository.findByOwnernameAndPassword(ownername, password);

        Member member = memberRepository.findByOwnernameAndPassword(ownername, password)
                .orElseThrow(IllegalArgumentException::new);

        return member;
//        if(memberOptional.isPresent()) {
//            throw new IllegalArgumentException();
//        } else {
//            return memberOptional.get();
//        }

    }

    /**
     * 회원가입
     */

    private void validateDuplicateMember(String ownername) {
        Optional<Member> memberOptional = memberRepository.findByOwnername(ownername);

        if(memberOptional.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public MemberDTO createMember(String ownername, String password, String location) {
        validateDuplicateMember(ownername);

        Long id = Long.valueOf(memberRepository.count() + 1);
        Member user = new Member();
        user.setId(id);
        user.setOwnername(ownername);
        user.setPassword(password);
        user.setLocation(location);
        memberRepository.save(user);
        return new MemberDTO(id, ownername, location);
    }

    /**
     * 회원 조회
     */
    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(member -> new MemberDTO(member.getId(), member.getOwnername(), member.getLocation()));
    }

    /**
     * 전체 회원 조회
     */
    public List<MemberDTO> findMembers() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberDTO(member.getId(), member.getOwnername(), member.getLocation()))
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
