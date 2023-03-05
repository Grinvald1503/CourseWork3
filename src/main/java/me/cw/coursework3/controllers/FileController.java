package me.cw.coursework3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cw.coursework3.services.SocksService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/socks/files")
@Tag(name = "Приложение работы с файлами", description = "Импорт/экспорт файлов носков")
@RequiredArgsConstructor

public class FileController {
    private SocksService socksService;
    @GetMapping("/export")
    @Operation(summary = "Выгрузка json-файла носков")
    public ResponseEntity<InputStreamResource> downloadSocksJson () {
        try {
            File socksFile = socksService.exportFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(socksFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(socksFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + socksFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/import")
    @Operation(summary = "Загрузка json-файла носков")
    public ResponseEntity<String> uploadSocksJson (@RequestParam MultipartFile file) {
        try {
            socksService.importFromFile(file);
            return ResponseEntity.ok("файл успешно импортирован");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла, проверьте корректность файла");
        }
    }
}
