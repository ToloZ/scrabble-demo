package com.example.demo.controller;

import com.example.demo.service.scrabble.ScrabbleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scrabble")
@RequiredArgsConstructor
public class ScrabbleController {

    private final ScrabbleService scrabbleService;

    @ResponseBody
    @GetMapping(value = "/words/{letters}", produces = "application/json")
    public List<String> getWords(@PathVariable("letters") String letters) {
        return scrabbleService.getWords(letters.toLowerCase());
    }
}
