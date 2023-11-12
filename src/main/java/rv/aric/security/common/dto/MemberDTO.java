package rv.aric.security.common.dto;

import java.util.UUID;

public record MemberDTO(
    UUID id,
    String name,
    String username,
    String email,
    String password,
    String biography
) {

}
