package playground.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class FilmEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
