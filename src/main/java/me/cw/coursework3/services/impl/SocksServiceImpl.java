package me.cw.coursework3.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import me.cw.coursework3.exception.ValidationException;
import me.cw.coursework3.model.Color;
import me.cw.coursework3.model.Size;
import me.cw.coursework3.model.Socks;
import me.cw.coursework3.model.SocksBatch;
import me.cw.coursework3.repository.SocksRepository;
import me.cw.coursework3.services.FileService;
import me.cw.coursework3.services.SocksService;
import me.cw.coursework3.services.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;
    private final ValidationService validationService;
    private final FileService fileService;
    @Value("${path.to.socks.file}")
    private String dataFilePath;
    @Value("${name.to.socks.file}")
    private String dataFileName;



    @Override
    public void accept(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);

        socksRepository.save(socksBatch);

    }

    @Override
    public int issuance(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);

        return socksRepository.remove(socksBatch);
    }

    @Override
    public int reject(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);

        return socksRepository.remove(socksBatch);
    }

    @Override
    public int getCount(Color color, Size size, int cottonMin, int cottonMax) {
        if (!validationService.validate(color, size, cottonMin, cottonMax)) {
            throw new ValidationException();
        }

        Map<Socks, Integer> socksMap = socksRepository.getAll();
        for (Map.Entry<Socks, Integer> socksItem : socksMap.entrySet()) {
            Socks socks = socksItem.getKey();
            if (socks.getColor().equals(color) &&
                    socks.getSize().equals(size) &&
                    socks.getCottonPart() >= cottonMin &&
                    socks.getCottonPart() <= cottonMax) {
                return socksItem.getValue();
            }

        }
        return 0;
    }

    @Override
    public File exportFile() throws IOException {
        return fileService.saveToFile(socksRepository.getList(), Path.of(dataFilePath, dataFileName)).toFile();
    }

    @Override
    public void importFromFile(MultipartFile file) throws IOException {
        List<SocksBatch> socksBatcheList = fileService.uploadFromFile(file, Path.of(dataFilePath, dataFileName), new TypeReference<List<SocksBatch>>() {
        });
        socksRepository.replace(socksBatcheList);

    }

    private void checkSocksBatch (SocksBatch socksBatch) {
        if (!validationService.validate(socksBatch)) {
            throw new ValidationException();
        }

    }
}
