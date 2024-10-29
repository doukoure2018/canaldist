package com.dist.canal.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportFileDto {

    private String NUMDIST;
    private String NOMDIST;
    private String DATE;
    private String CMOUVMT;
    private String MONTANT_TTC;
    private String MONTANT_HT;
    private String LARTICLE;
    private String DEBABO;
    private String FINABO;
    private String NUMCARTE;

}

