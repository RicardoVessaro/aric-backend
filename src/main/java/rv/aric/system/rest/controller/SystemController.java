package rv.aric.system.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {
    @GetMapping("/check")
    public String check() {
        return "OK";
    }

    @GetMapping("/auth/check")
    public String authCheck() {
        return "AUTH OK";
    }
}
