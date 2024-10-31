package com.dist.canal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "statistique")
public class Statistique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String distprinc;
    private String numdist;
    private String nomdist;
    @Temporal(TemporalType.DATE)
    private Date createAt;
    private String cmouvmt;
    @Temporal(TemporalType.DATE)
    private Date debabo;
    @Temporal(TemporalType.DATE)
    private Date finabo;
    private String numcarte;
    private BigDecimal commission;
    private BigDecimal montant;
    private String larticle;

}
