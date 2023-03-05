package me.cw.coursework3.services;

import me.cw.coursework3.model.Color;
import me.cw.coursework3.model.Size;
import me.cw.coursework3.model.SocksBatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface SocksService {
    void accept(SocksBatch socksBatch);

    int issuance(SocksBatch socksBatch);

    int reject(SocksBatch socksBatch);

    int getCount(Color color, Size size, int cottonMin, int cottonMax);

    File exportFile() throws IOException;

    void importFromFile(MultipartFile file) throws IOException;
}
