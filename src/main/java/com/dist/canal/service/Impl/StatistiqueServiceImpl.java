package com.dist.canal.service.Impl;

import com.dist.canal.entity.Statistique;
import com.dist.canal.enumeration.ColumnName;
import com.dist.canal.payload.ImportFileDto;
import com.dist.canal.payload.StatistiqueDto;
import com.dist.canal.repository.StatistiqueRepository;
import com.dist.canal.service.StatistiqueService;
import com.dist.canal.utils.ExcelUtils;
import com.dist.canal.utils.FileFactory;
import com.dist.canal.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatistiqueServiceImpl implements StatistiqueService {

    private final StatistiqueRepository statistiqueRepository;
    private final ModelMapper mapper;

    String[] constraints = {"EVASION", "ACCESS", "ESSENTIEL+", "EVASION+", "ACCESS+", "TOUT CANAL", "TOUT CANAL+"};
    String[] constraints2 = {"REAAV", "REAAP", "MODART", "CREAT"};
    String[] montantContraint= {"160000","220000","80000","380000"};

    @Override
    public StatistiqueDto saveState(MultipartFile importFile) {
        String filename = importFile.getOriginalFilename();
        List<ImportFileDto> importFileDtoList;
        if (filename != null && filename.endsWith(".xlsx")) {
            importFileDtoList = parseExcelFile(importFile);
        } else if (filename != null && filename.endsWith(".csv")) {
            importFileDtoList = parseCsvFile(importFile);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .csv and .xlsx are allowed.");
        }
        List<Statistique> statistiques = importFileDtoList.stream()
                .map(this::addStatistique)
                .collect(Collectors.toList());
        statistiqueRepository.saveAll(statistiques);
        return statistiques.isEmpty() ? null : mapper.map(statistiques.get(0), StatistiqueDto.class);
    }

    private List<ImportFileDto> parseExcelFile(MultipartFile importFile) {
        try (Workbook workbook = FileFactory.getWorkbookStream(importFile)) {
            return ExcelUtils.getImportData(workbook, ImportConfig.customerImport);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
    }

    private ImportFileDto convertRecordToDto(CSVRecord record) {
        ImportFileDto dto = new ImportFileDto();
        dto.setDISTPRINC(record.get(ColumnName.DISTPRINC.name()));
        dto.setNUMDIST(record.get(ColumnName.NUMDIST.name()));
        dto.setNOMDIST(record.get(ColumnName.NOMDIST.name()));
        dto.setDATE(parseDateString(record.get(ColumnName.DATE.name())));
        dto.setCMOUVMT(record.get(ColumnName.CMOUVMT.name()));
        String montant_ttc = record.get(ColumnName.MONTANT_TTC.name());
        BigDecimal montant_ttc_bd = new BigDecimal(montant_ttc);
        dto.setMONTANT_TTC(montant_ttc_bd);
        // Commission
        String montant_ht = record.get(ColumnName.MONTANT_HT.name());
        BigDecimal montant_ht_bd = new BigDecimal(montant_ht);
        dto.setMONTANT_HT(montant_ht_bd);
        dto.setLARTICLE(record.get(ColumnName.LARTICLE.name()));
        dto.setDEBABO(parseDateString(record.get(ColumnName.DEBABO.name())));
        dto.setFINABO(parseDateString(record.get(ColumnName.FINABO.name())));
        dto.setNUMCARTE(record.get(ColumnName.NUMCARTE.name()));
        return dto;
    }

    private List<ImportFileDto> parseCsvFile(MultipartFile importFile) {
        List<ImportFileDto> importFileDtoList = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(importFile.getInputStream()), StandardCharsets.UTF_8));
             CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader())) {

            for (CSVRecord record : csvParser) {
                // Debugging Lines
                System.out.println("Record size: " + record.size());
                for(int i = 0; i < record.size(); i++) {
                    System.out.println("Index " + i + " : " + record.get(i));
                }

                importFileDtoList.add(convertRecordToDto(record));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
        return importFileDtoList;
    }

    private Date parseDateString(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(dateString);
        } catch (ParseException pe) {
            throw new RuntimeException("Failed to parse date string: " + dateString, pe);
        }
    }

    private Statistique addStatistique(ImportFileDto dto) {
        Statistique statistique = new Statistique();

        boolean containsContraint2 = Arrays.asList(constraints2).contains(dto.getCMOUVMT());
        boolean containsContraint = !Arrays.asList(constraints).contains(dto.getLARTICLE());
        boolean containsMontantContraint = Arrays.asList(montantContraint).contains(dto.getMONTANT_TTC().toString());

        if (containsContraint2 || containsContraint) {
            if (("REAAV".equals(dto.getCMOUVMT()) || "REAAP".equals(dto.getCMOUVMT())) && containsMontantContraint) {
                BigDecimal montant = new BigDecimal(String.valueOf(dto.getMONTANT_TTC()));
                statistique.setMontant(montant);
                BigDecimal commission = new BigDecimal(String.valueOf(dto.getMONTANT_HT())); // Assuming commission is MONTANT_HT
                statistique.setCommission(commission);
            } else {
                statistique.setMontant(BigDecimal.ZERO);
                BigDecimal commission = new BigDecimal(String.valueOf(dto.getMONTANT_HT()));
                statistique.setCommission(commission);
            }

            if ("CREAT".equals(dto.getCMOUVMT()) && containsContraint) {
                return statistique; // If constraints aren't met, return the current 'statistique' object
            }
        }
        statistique.setDistprinc(dto.getDISTPRINC());
        statistique.setNumdist(dto.getNUMDIST());
        statistique.setNomdist(dto.getNOMDIST());
        statistique.setCreateAt(dto.getDATE());
        statistique.setCmouvmt(dto.getCMOUVMT());
        statistique.setLarticle(dto.getLARTICLE());
        statistique.setDebabo(dto.getDEBABO());
        statistique.setFinabo(dto.getFINABO());
        statistique.setNumcarte(dto.getNUMCARTE());
        return statistique;
    }


}
