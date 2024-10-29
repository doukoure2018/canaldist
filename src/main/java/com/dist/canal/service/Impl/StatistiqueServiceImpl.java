package com.dist.canal.service.Impl;

import com.dist.canal.entity.Statistique;
import com.dist.canal.payload.ImportFileDto;
import com.dist.canal.payload.StatistiqueDto;
import com.dist.canal.repository.StatistiqueRepository;
import com.dist.canal.service.StatistiqueService;
import com.dist.canal.utils.ExcelUtils;
import com.dist.canal.utils.FileFactory;
import com.dist.canal.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatistiqueServiceImpl implements StatistiqueService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");



    private final StatistiqueRepository statistiqueRepository;
    private final ModelMapper mapper;

    @Override
    public StatistiqueDto saveState(MultipartFile importFile) {
        String filename = importFile.getOriginalFilename();
        List<ImportFileDto> importFileDtoList;
        // Check file format and parse accordingly
        if (filename != null && filename.endsWith(".xlsx")) {
            importFileDtoList = parseExcelFile(importFile);
        } else if (filename != null && filename.endsWith(".csv")) {
            importFileDtoList = parseCsvFile(importFile);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .csv and .xlsx are allowed.");
        }
        // Convert ImportFileDto list to Statistique entities
        List<Statistique> statistiques = importFileDtoList.stream()
                .map(this::addStatistique)
                .collect(Collectors.toList());
        // Save all statistiques to the database
        statistiqueRepository.saveAll(statistiques);
        // Assuming we want to return a StatistiqueDto from the first entity
        return statistiques.isEmpty() ? null : mapper.map(statistiques.get(0), StatistiqueDto.class);
    }

    private List<ImportFileDto> parseExcelFile(MultipartFile importFile) {
        try (Workbook workbook = FileFactory.getWorkbookStream(importFile)) {
            return ExcelUtils.getImportData(workbook, ImportConfig.customerImport);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
    }

    private List<ImportFileDto> parseCsvFile(MultipartFile importFile) {
        List<ImportFileDto> importFileDtoList = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(importFile.getInputStream(), StandardCharsets.UTF_8, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : csvParser) {
                ImportFileDto dto = new ImportFileDto();
                dto.setNUMDIST(record.get("NUMDIST"));
                dto.setNOMDIST(record.get("NOMDIST"));
                dto.setDATE(record.get("DATE"));
                dto.setCMOUVMT(record.get("CMOUVMT"));
                dto.setMONTANT_TTC(record.get("MONTANT_TTC"));
                dto.setLARTICLE(record.get("LARTICLE"));
                dto.setDEBABO(record.get("DEBABO"));
                dto.setFINABO(record.get("FINABO"));
                dto.setNUMCARTE(record.get("NUMCARTE"));
                importFileDtoList.add(dto);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
        return importFileDtoList;
    }

    private Statistique addStatistique(ImportFileDto dto) {
        Statistique statistique = new Statistique();


        statistique.setNumdist(dto.getNUMDIST());
        statistique.setNomdist(dto.getNOMDIST());
        statistique.setCreateAt(dto.getDATE());
        statistique.setCmouvmt(dto.getCMOUVMT());
        statistique.setMontant(dto.getMONTANT_TTC());
        statistique.setLarticle(dto.getLARTICLE());
        statistique.setDebabo(dto.getDEBABO());
        statistique.setFinabo(dto.getFINABO());
        statistique.setNumcarte(dto.getNUMCARTE());
        return statistique;
    }


}
