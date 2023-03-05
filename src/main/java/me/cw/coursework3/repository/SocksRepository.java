package me.cw.coursework3.repository;

import me.cw.coursework3.model.Socks;
import me.cw.coursework3.model.SocksBatch;

import java.util.List;
import java.util.Map;

public interface SocksRepository {
    void save(SocksBatch socksBatch);

    int remove(SocksBatch socksBatch);
    Map<Socks, Integer> getAll();
    List<SocksBatch> getList();
    void replace(List<SocksBatch> socksBatchList);
}
