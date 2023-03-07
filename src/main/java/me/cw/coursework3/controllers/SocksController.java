package me.cw.coursework3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cw.coursework3.controllers.dto.ResponseDto;
import me.cw.coursework3.model.Color;
import me.cw.coursework3.model.Size;
import me.cw.coursework3.model.SocksBatch;
import me.cw.coursework3.services.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Приложение для учёта носков", description = "Учёт прихода, отгрузки, брака, остатка")
@RequiredArgsConstructor
public class SocksController {
    private final SocksService socksService;

    @PostMapping
    @Operation(summary = "Регистрация прихода товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "удалось добавить приход"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})

    public ResponseEntity<ResponseDto> addSocks(@RequestBody SocksBatch socksBatch) {
        socksService.accept(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Носки успешно добавлены на склад"));
    }
    @PutMapping
    @Operation(summary = "Отпуск носков со склада")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "удалось произвести отпуск носков со склада"),
            @ApiResponse(responseCode = "400", description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})

    public ResponseEntity<ResponseDto> shipmentSocks(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksService.issuance(socksBatch);
        return ResponseEntity.ok(new ResponseDto(socksCount + " носков отпущено со склада"));
    }
    @GetMapping
    @Operation(summary = "Возвращает общее количество носков на складе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})

    public ResponseEntity<ResponseDto> getSocks(@RequestBody Color color,
                                         @RequestBody Size size,
                                         @RequestBody int cottonMin,
                                         @RequestBody int cottonMax) {
        int socksCount = socksService.getCount(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(new ResponseDto("колличество носков : " + socksCount));
    }
    @DeleteMapping
    @Operation(summary = "Регистрирует списание испорченных (бракованных) носков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "запрос выполнен, товар списан со склада"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})

    public ResponseEntity<ResponseDto> deleteSocks(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksService.issuance(socksBatch);
        return ResponseEntity.ok(new ResponseDto(socksCount + " носков списано со склада"));
    }


}
