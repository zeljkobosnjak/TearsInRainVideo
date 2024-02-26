package org.tearsinrainvideo.tearsinrainvideo.service;

import org.tearsinrainvideo.tearsinrainvideo.model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film saveFilm(Film film);
    Optional<Film> findById(Long id);
    List<Film> findAll();
    void deleteFilm(Long id);
}
