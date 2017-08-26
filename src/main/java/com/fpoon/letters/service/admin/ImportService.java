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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            MappingIterator<Letter> mapper = getCsvMapper(Letter.class, file.getInputStream());
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
            log.error("Failed to import letter set: ", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Word> readWordCsv(MultipartFile file) {
        try {
            log.info("Importing word set {}", file.getOriginalFilename());

            MappingIterator<Word> mapper = getCsvMapper(Word.class, file.getInputStream());
            List<Word> words = new ArrayList<>();
            long counter;
            for (counter = 0; mapper.hasNextValue(); counter++) {
                if (words.size() > 100) {
                    wordRepository.save(words);
                    log.info("Imported batch {} : {} -> {}", counter/100, words.get(0).getWord(), words.get(words.size()-1).getWord());
                    words.clear();
                }
                words.add(mapper.nextValue());
            }
            wordRepository.save(words);

            log.info("Imported {} words", counter);
            return words;
        } catch (Exception e) {
            log.error("Failed to import letter set: ", e.getMessage());
            return Collections.emptyList();
        }
    }

    private  <T> MappingIterator<T> getCsvMapper(Class<T> type, InputStream inputStream) {
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withEscapeChar('\\');
            CsvMapper mapper = new CsvMapper();
            return mapper.readerFor(type).with(schema).readValues(inputStream);
        } catch (Exception e) {
            log.error("Failed to instantiate CSV reader: {}", e.getMessage());
            return null;
        }
    }
}
