package ATBlib.controller;

import ATBlib.dto.ProfileDto;
import ATBlib.exceptions.ResourceNotFoundException;
import ATBlib.model.auth.User;
import ATBlib.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin( origins = {"http://localhost:3000"} )
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ProfileDto aboutCurrentUser(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти пользователя. Имя пользователя: " + principal.getName()));
        return new ProfileDto(user.getUsername(), user.getEmail());
    }
}
