package com.fpoon.letters.controller;

import com.fpoon.letters.domain.Letter;
import com.fpoon.letters.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/{letter}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void put(@PathVariable Character letter, @RequestParam("score") Integer score) {
        letterService.put(letter, score);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Letter> getAll() {
        return letterService.getAll();
    }

}
