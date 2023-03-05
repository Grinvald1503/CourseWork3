package me.cw.coursework3.services.impl;

import me.cw.coursework3.model.Color;
import me.cw.coursework3.model.Size;
import me.cw.coursework3.model.SocksBatch;
import me.cw.coursework3.services.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(SocksBatch socksBatch) {
        return socksBatch.getSocks() != null &&
                socksBatch.getQuantity() > 0 &&
                socksBatch.getSocks().getColor() != null &&
                socksBatch.getSocks().getSize() != null &&
                checkCotton(socksBatch.getSocks().getCottonPart(), socksBatch.getSocks().getCottonPart());
    }

    @Override
    public boolean validate(Color color, Size size, int cottonMin, int cottonMax) {
        return color != null && size != null && checkCotton(cottonMin, cottonMax);
    }
    private boolean checkCotton(int cottonMin, int cottonMax) {
        return cottonMin >= 0 && cottonMax <= 100;
    }
}
