package com.linkup.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkup.auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UsersDTO {

    private Integer id;

    @NotBlank(message = "Name can not be blank")
    @Size(min = 4, message = "Username must be min of 4 characters !")
    private String name;

    @Email(message = "Email address is not valid")
    @NotBlank(message = "Email can not be blank")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 4, max = 13, message = "Password must be min of 3 characters and max of 13 characters !")
    private String password;

    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    public Integer getId(){
        return this.id;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password=password;
    }
}
