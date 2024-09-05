package hello.hello_spring.entity;

import jakarta.persistence.*;


@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    private String storename;

    private String storedetail;

}
