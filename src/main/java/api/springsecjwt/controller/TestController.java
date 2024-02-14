package api.springsecjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/anon")
    public String anonEndPoint() {
        return "everyone can see this";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String usersEndPoint() {
        return "ONLY users can see this";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminsEndPoint() {
        System.out.println("adminsEndPoint");
        return "ONLY admins can see this";
    }
}
