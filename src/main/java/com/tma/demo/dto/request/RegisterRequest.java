package com.tma.demo.dto.request;


import lombok.Data;


@Data
public class RegisterRequest {

    private String firstName;
   
    private String lastName;
  
    private String email;
   
    private String phoneNumber;
    
    private String password;
    
    private String confirmPassword;
}
