package com.dist.canal.controller;

import com.dist.canal.payload.HttpResponse;
import com.dist.canal.payload.UserDto;
import com.dist.canal.service.AuthService;
import com.dist.canal.service.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/state/securecanal")
@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;
    private final AuthService authService;

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<HttpResponse> importStatistique(
            @AuthenticationPrincipal UserDto user,
            @RequestParam("file") MultipartFile file
    )  {
        statistiqueService.saveState(file);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of(
                                "user", authService.getUserByEmail(user.getEmail())
                                ))
                        .message("Statistiques Imported Successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
}
