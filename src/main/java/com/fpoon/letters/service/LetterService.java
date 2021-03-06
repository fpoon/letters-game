package com.fpoon.letters.service;

import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    @Transactional
    public Letter get(Character letter) {
        return letterRepository.findOne(letter);
    }

    @Transactional
    @Modifying
    public void put(Character letter, Integer score, Integer quantity) {
        letterRepository.save(new Letter(letter, score, quantity));
    }

    @Transactional
    public List<Letter> getAll() {
        return letterRepository.findAll();
    }


}
