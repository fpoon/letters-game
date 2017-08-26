package com.fpoon.letters.repository;

import com.fpoon.letters.domain.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, String> {
}
