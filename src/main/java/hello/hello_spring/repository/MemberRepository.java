package hello.hello_spring.repository;

import hello.hello_spring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOwnername(String ownername);

    Optional<Member> findByPassword(String passwword);
}
