package com.fpoon.letters.controller;

import com.fpoon.letters.domain.Letter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/letter")
public class LettersController {

    @RequestMapping(value = "/{letter}", method = RequestMethod.GET)
    @ResponseBody
    public Letter get(@PathVariable Character letter) {
        return new Letter(letter, 1);
    }

}
