package me.cw.coursework3.services;
import me.cw.coursework3.model.Color;
import me.cw.coursework3.model.Size;
import me.cw.coursework3.model.SocksBatch;

public interface ValidationService {
    boolean validate(SocksBatch socksBatch);
    boolean validate(Color color, Size size, int cottonMin, int cottonMax);
}
