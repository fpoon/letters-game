package com.fpoon.letters.service.admin;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.domain.Word;
import com.fpoon.letters.repository.LetterRepository;
import com.fpoon.letters.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ImportService {

    Logger log = LoggerFactory.getLogger(ImportService.class);

    private final LetterRepository letterRepository;
    private final WordRepository wordRepository;

    public List<Letter> readLetterCsv(MultipartFile file) {
        try {
            log.info("Importing letter set {}", file.getOriginalFilename());

            List<Letter> letters = new ArrayList<>();
            MappingIterator<Letter> mapper = getCsvMapper(Letter.class, file.getInputStream(), true);
            while (mapper.hasNextValue()) {
                Letter l = mapper.nextValue();
                if (l.getQuantity() > 0)
                    letters.add(l);
            }

            letterRepository.deleteAll();
            letterRepository.save(letters);

            log.info("Imported {} letters", letters.size());
            return letters;
        } catch (Exception e) {
            log.error("Failed to import letter set: {}: {} ", e.getClass().getSimpleName(), e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Word> readWordCsv(MultipartFile file) {
        try {
            log.info("Importing word set {}", file.getOriginalFilename());

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<Word> words = new ArrayList<>();
            AtomicReference<Long> counter = new AtomicReference<>(0L);
            reader.lines().forEach( line -> {
                if (words.size() > 1000) {
                    wordRepository.save(words);
                    log.info("Imported batch {} : {} -> {}", counter.get()/1000, words.get(0).getWord(), words.get(words.size()-1).getWord());
                    words.clear();
                }
                words.add(new Word(line.trim()));
                counter.set(counter.get()+1);
            });
            wordRepository.save(words);

            log.info("Imported {} words", counter);
            return words;
        } catch (Exception e) {
            log.error("Failed to import word set: {}: {}", e.getClass().getSimpleName(), e.getMessage());
            return Collections.emptyList();
        }
    }

    private  <T> MappingIterator<T> getCsvMapper(Class<T> type, InputStream inputStream, boolean withHeader) {
        try {
            CsvSchema schema;
            if (withHeader)
                schema = CsvSchema.emptySchema().withHeader().withEscapeChar('\\');
            else
                schema = CsvSchema.emptySchema().withEscapeChar('\\');
            CsvMapper mapper = new CsvMapper();
            return mapper.readerFor(type).with(schema).readValues(inputStream);
        } catch (Exception e) {
            log.error("Failed to instantiate CSV reader: {}", e.getMessage());
            return null;
        }
    }
}
