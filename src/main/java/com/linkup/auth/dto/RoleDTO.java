package com.linkup.auth.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RoleDTO {
    @Id
    private Integer id;
    private String name;
}
