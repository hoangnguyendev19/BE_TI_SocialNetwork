package com.tma.demo.dto.request;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPasswordRequest {

    private String newPassword;
   
    private String confirmNewPassword;
}
