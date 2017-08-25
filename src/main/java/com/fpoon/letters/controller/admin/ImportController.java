package com.fpoon.letters.controller.admin;

import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.service.admin.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/import")
@RequiredArgsConstructor
public class ImportController {

    private final ImportService importService;

    @RequestMapping(value = "/letters", method = RequestMethod.POST)
    @ResponseBody
    public List<Letter> importLetters(@RequestParam("file") MultipartFile file) {
        return importService.readLetterCsv(file);
    }
}
