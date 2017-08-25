package com.fpoon.letters.service.admin;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportService {

    Logger log = LoggerFactory.getLogger(ImportService.class);

    private final LetterRepository letterRepository;

    public List<Letter> readLetterCsv(MultipartFile file) {
        try {
            log.info("Importing letter set {}", file.getOriginalFilename());

            List<Letter> letters = readCsv(Letter.class, file.getInputStream());
            letterRepository.deleteAll();
            letterRepository.save(letters);

            log.info("Imported {} letters", letters.size());
            return letters;
        } catch (Exception e) {
            log.error("Failed to import letter set: ", e.getMessage());
            return Collections.emptyList();
        }
    }

    private  <T> List<T> readCsv(Class<T> type, InputStream inputStream) {
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<T> values = mapper.readerFor(type).with(schema).readValues(inputStream);
            return values.readAll();
        } catch (Exception e) {
            log.error("Failed to read CSV: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
