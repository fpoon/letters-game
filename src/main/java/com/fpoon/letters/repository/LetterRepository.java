package com.fpoon.letters.repository;

import com.fpoon.letters.domain.Letter;
import org.springframework.data.repository.CrudRepository;

public interface LetterRepository extends CrudRepository<Letter, Character> {

}
