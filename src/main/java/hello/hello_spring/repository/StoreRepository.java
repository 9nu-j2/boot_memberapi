package hello.hello_spring.repository;

import hello.hello_spring.dto.StoreDTO;
import hello.hello_spring.entity.Member;
import hello.hello_spring.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByMember(Member member);
}
