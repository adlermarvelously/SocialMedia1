package com.merveadler.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class UserRole {
    @Id
    String id;
    String name; // Admin, AhmetAmca
    String description;
    Long authid;
    Long userid;
}
