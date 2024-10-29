package com.dist.canal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "statistique")
public class Statistique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numdist;
    private String nomdist;
    private String createAt;
    private String cmouvmt;
    private String debabo;
    private String finabo;
    private String numcarte;
    private String commission;
    private String montant;
    private String larticle;

}