package com.fpoon.letters.service;

import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    @Transactional
    public Letter get(Character letter) {
        return letterRepository.findOne(letter);
    }

}
