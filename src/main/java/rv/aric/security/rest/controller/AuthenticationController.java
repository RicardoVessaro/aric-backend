package rv.aric.security.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rv.aric.security.common.dto.MemberDTO;
import rv.aric.security.rest.request.AuthenticationRequest;
import rv.aric.security.common.response.AuthenticationResponse;
import rv.aric.security.service.AuthenticationService;

@RestController
@RequestMapping("/aric/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody MemberDTO memberDTO
    ) {
        return ResponseEntity.ok(authenticationService.register(memberDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(
            authenticationRequest.username(),
            authenticationRequest.password()
        );

        return ResponseEntity.ok(authenticationResponse);
    }

}
