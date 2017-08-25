package com.fpoon.letters.controller;

import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/letter")
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;

    @RequestMapping(value = "/{letter}", method = RequestMethod.GET)
    @ResponseBody
    public Letter get(@PathVariable Character letter) {
        return letterService.get(letter);
    }

}
