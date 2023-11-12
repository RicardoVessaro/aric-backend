package rv.aric.security.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rv.aric.security.common.dto.MemberDTO;
import rv.aric.security.rest.request.AuthenticationRequest;
import rv.aric.security.rest.response.AuthenticationResponse;
import rv.aric.security.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody MemberDTO memberDTO
    ) {
        String token = authenticationService.register(memberDTO);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        String token = authenticationService.authenticate(
            authenticationRequest.username(),
            authenticationRequest.password()
        );

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
