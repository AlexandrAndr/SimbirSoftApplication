package ru.meschanov.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.meschanov.domains.WordsEntity;

@Repository
public interface WordsRepository extends CrudRepository<WordsEntity, Long> {
}
