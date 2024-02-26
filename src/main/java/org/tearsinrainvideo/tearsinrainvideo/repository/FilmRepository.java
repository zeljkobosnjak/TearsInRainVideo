package org.tearsinrainvideo.tearsinrainvideo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tearsinrainvideo.tearsinrainvideo.model.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {

}
