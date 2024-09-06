package hello.hello_spring.controller;

import hello.hello_spring.dto.StoreDTO;
import hello.hello_spring.entity.Store;
import hello.hello_spring.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/members/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }


    // 특정 Member가 소유한 Store 목록 조회
    @Operation(summary = "Get all stores of member", description = "Fetch all stores member")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fetched all stores member", content = @Content(schema = @Schema(implementation = Store.class)))
    })
    @GetMapping("/{ownername}")
    public ResponseEntity<List<Store>> getStoresByMemberId(@PathVariable(name = "ownername", required = true) String ownername) {
        List<Store> stores = storeService.getStoresByMemberOwnername(ownername);
        return ResponseEntity.ok(stores);
    }

    @PostMapping("/{ownername}/add")
    public ResponseEntity<Store> addStoreToMember(@PathVariable(name = "ownername", required = true) String ownername, @RequestBody StoreDTO store) {
        Store createdStore = storeService.addStoreToMember(ownername, store);
        return ResponseEntity.status(200).body(createdStore);
    }
}
