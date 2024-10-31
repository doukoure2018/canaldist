package com.dist.canal.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatistiqueDto {
    private Long id;
    private String distprinc;
    private String numdist;
    private String nomdist;
    private Date createAt;
    private String cmouvmt;
    private Date debabo;
    private Date finabo;
    private String numcarte;
    private BigDecimal commission;
    private BigDecimal montant;
    private String larticle;
}
