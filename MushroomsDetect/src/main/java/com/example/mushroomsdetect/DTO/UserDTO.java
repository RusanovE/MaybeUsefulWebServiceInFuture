package com.example.mushroomsdetect.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    String login;
    String password;
    String role;
    byte[] photo;
}
