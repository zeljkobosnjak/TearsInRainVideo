package org.tearsinrainvideo.tearsinrainvideo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tearsinrainvideo.tearsinrainvideo.exception.FilmNotFoundException;
import org.tearsinrainvideo.tearsinrainvideo.model.Film;
import org.tearsinrainvideo.tearsinrainvideo.repository.FilmRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.ofNullable(filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with id " + id + " not found.")));
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public void deleteFilm(Long id) {
        if (!filmRepository.existsById(id)) {
            throw new FilmNotFoundException("Attempted to delete a non-existent film with id " + id + ".");
        }
        filmRepository.deleteById(id);
    }
}
