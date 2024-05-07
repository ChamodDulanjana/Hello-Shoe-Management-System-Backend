package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(unique = true, nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 16)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Role role;
}
