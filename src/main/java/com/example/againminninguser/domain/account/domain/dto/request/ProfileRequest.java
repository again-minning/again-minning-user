package com.example.againminninguser.domain.account.domain.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileRequest {
    private MultipartFile profile;
}
