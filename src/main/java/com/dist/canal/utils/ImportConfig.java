package com.dist.canal.utils;

import com.dist.canal.enumeration.ColumnName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.dist.canal.payload.ImportFileDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {

    private int sheetIndex;

    private int headerIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellImportConfigs;

    public static final ImportConfig customerImport;

    static {
        customerImport = new ImportConfig();
        customerImport.setSheetIndex(0);
        customerImport.setHeaderIndex(0);
        customerImport.setStartRow(1);
        customerImport.setDataClazz(ImportFileDto.class);
        List<CellConfig> customerImportCellConfigs = new ArrayList<>();
        customerImportCellConfigs.add(new CellConfig(0, ColumnName.DISTPRINC.name()));
        customerImportCellConfigs.add(new CellConfig(1, ColumnName.NUMDIST.name()));
        customerImportCellConfigs.add(new CellConfig(2, ColumnName.NOMDIST.name()));
        customerImportCellConfigs.add(new CellConfig(12, ColumnName.DATE.name()));
        customerImportCellConfigs.add(new CellConfig(13, ColumnName.CMOUVMT.name()));
        customerImportCellConfigs.add(new CellConfig(14, ColumnName.MONTANT_TTC.name()));
        customerImportCellConfigs.add(new CellConfig(15, ColumnName.MONTANT_HT.name()));
        customerImportCellConfigs.add(new CellConfig(21, ColumnName.LARTICLE.name()));
        customerImportCellConfigs.add(new CellConfig(22, ColumnName.DEBABO.name()));
        customerImportCellConfigs.add(new CellConfig(23, ColumnName.FINABO.name()));
        customerImportCellConfigs.add(new CellConfig(26, ColumnName.NUMCARTE.name()));
        customerImport.setCellImportConfigs(customerImportCellConfigs);
    }
}

