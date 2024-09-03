package hello.hello_spring.controller;

import hello.hello_spring.dto.MemberDTO;
import hello.hello_spring.dto.MemberForm;
import hello.hello_spring.entity.Member;
import hello.hello_spring.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "Create a new member", description = "Add a new member to the system")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Memeber created successfully",
                    content = @Content(schema = @Schema(implementation = MemberDTO.class)),
                    headers = @Header(name = "Location", description = "The URI of the newly created member", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberForm memberForm){
        MemberDTO createdMember = memberService.createMember(memberForm.getEmail(), memberForm.getPassword());
        return ResponseEntity.status(201).header("Location", "/members/" + createdMember.getId()).body(createdMember);
    }

    @Operation(summary = "Get all members", description = "Fetch all members")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fetched all members", content = @Content(schema = @Schema(implementation = MemberDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.findMembers();
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "Get member by ID", description = "Fetch a member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the member", content = @Content(schema = @Schema(implementation = MemberDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        Optional<MemberDTO> member = memberService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{seq}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long seq) {
        boolean deleted = memberService.deleteMember(seq);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
