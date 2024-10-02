    package com.example.homestay.model;

    import com.example.homestay.enums.UserStatus;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import java.time.LocalDate;
    import java.util.Collection;
    import java.util.Collections;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "user")
    public class User extends AuditEntity implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String username;

        private String password;

        private String fullname;

        private String email;

        private String phone;

        private String address;

        @Column(name = "date_of_birth")
        private LocalDate dob;

        @Enumerated(EnumType.ORDINAL)
        private UserStatus status;

//        @ManyToOne
//        @JoinColumn(name = "district_id")
//        private District district;

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return  Collections.singletonList(new SimpleGrantedAuthority(role.getAuthority()));
        }
    }

