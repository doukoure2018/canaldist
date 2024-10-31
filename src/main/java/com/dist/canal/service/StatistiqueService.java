package com.dist.canal.service;

import com.dist.canal.payload.StatistiqueDto;
import org.springframework.web.multipart.MultipartFile;

public interface StatistiqueService {

    StatistiqueDto saveState(MultipartFile file);
}
