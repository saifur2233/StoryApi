package com.example.storyapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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



    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "User email can't empty")
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty(message = "User phone number can't empty")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @NotEmpty(message ="User password can't empty")
    private String password;

    private LocalDateTime created_At = LocalDateTime.now();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Story> stories;

    public Users(int id,String name,String email, String password, String phoneNumber){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Users(String name,String email, String password, String phoneNumber){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public Users(String name,String password, String phoneNumber){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Users(String email, String password){
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id+email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Users other = (Users) obj;
        return Objects.equals(getId(), other.id) && Objects.equals(getEmail(), other.email);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", created_At=" + created_At +
                ", stories=" + stories +
                '}';
    }
}
