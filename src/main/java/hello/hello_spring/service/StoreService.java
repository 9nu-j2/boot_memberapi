package hello.hello_spring.service;

import hello.hello_spring.dto.MemberDTO;
import hello.hello_spring.dto.StoreDTO;
import hello.hello_spring.entity.Member;
import hello.hello_spring.entity.Store;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.StoreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
    private MemberRepository memberRepository;

    public StoreService(MemberRepository memberRepository, StoreRepository storeRepository) {
        this.memberRepository = memberRepository;
        this.storeRepository = storeRepository;
    }

    public List<Store> getStoresByMemberOwnername(String ownername) {
        Optional<Member> member = memberRepository.findByOwnername(ownername);
        if (member.isPresent()) {
            return storeRepository.findByMember(member.get());
        } else {
            throw new EntityNotFoundException("Member not found with ownername: " + ownername);
        }
    }

    public Store addStoreToMember(String ownername, StoreDTO store) {
        Optional<Member> memberOptional = memberRepository.findByOwnername(ownername);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Store storeToAdd = new Store();
            storeToAdd.setStoredetail(store.getStoredetail());
            storeToAdd.setStorename(store.getStorename());
            storeToAdd.setMember(member); // Store에 Member 연결
            return storeRepository.save(storeToAdd); // Store 저장
        } else {
            throw new EntityNotFoundException("Member not found with id: " + ownername);
        }
    }

}
