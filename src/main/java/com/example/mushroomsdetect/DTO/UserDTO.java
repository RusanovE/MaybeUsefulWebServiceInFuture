package com.example.mushroomsdetect.DTO;

import com.example.mushroomsdetect.entitys.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class UserDTO {
    Long id;
    String login;
    String password;
    Role role;
    MultipartFile photo;

}
