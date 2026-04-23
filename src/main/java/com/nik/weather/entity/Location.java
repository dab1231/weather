package com.nik.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "locations", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
