package ru.meschanov.domains;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "unique_words")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WordsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "find_words")
    private String word;

    @Column(name = "frequency")
    private Integer frequencyWord;

    public WordsEntity(String word, Integer frequencyWord){
        this.word = word;
        this.frequencyWord = frequencyWord;
    }
}
