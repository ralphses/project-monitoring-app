package com.clicks.project_monitoring.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class AdminDto {
    String reference;
    String name;
    String email;
    String role;
}
