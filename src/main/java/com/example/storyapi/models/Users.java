package com.example.storyapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Pattern(regexp = "^[A-Za-z\\s]+$",message = "user name should be valid")
    private String name;


    @Column(name="email", unique=true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "User email can't empty")
    private String  email;

    @NotNull
    @NotBlank
    @NotEmpty(message = "User phone number can't empty")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @NotEmpty(message ="User password can't empty")
    private String password;

    private LocalDateTime created_At = LocalDateTime.now();

    public Users(String email, String password){
        this.email = email;
        this.password = password;
    }

}
