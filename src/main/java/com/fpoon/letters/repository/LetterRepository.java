package com.fpoon.letters.repository;

import com.fpoon.letters.domain.Letter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LetterRepository extends CrudRepository<Letter, Character> {
    List<Letter> findAll();
}
