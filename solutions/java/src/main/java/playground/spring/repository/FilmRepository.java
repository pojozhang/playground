package playground.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.spring.entity.FilmEntity;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
}
