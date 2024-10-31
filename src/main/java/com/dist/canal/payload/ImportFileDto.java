package com.dist.canal.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImportFileDto {

    private String DISTPRINC; //0
    private String NUMDIST; // 1
    private String NOMDIST; // 2
    private Date DATE; //12
    private String CMOUVMT; // 13
    private BigDecimal MONTANT_TTC; // 14
    private BigDecimal MONTANT_HT; // 15 commission
    private String LARTICLE; // 21
    private Date DEBABO; // 22
    private Date FINABO; // 23
    private String NUMCARTE; // 26

}

