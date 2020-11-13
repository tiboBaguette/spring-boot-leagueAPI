package com.realdolmen.springbootapi.controller;

import com.realdolmen.springbootapi.model.Summoner;
import com.realdolmen.springbootapi.service.SummonerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class SummonerController {
    private SummonerService summonerService;

    @RequestMapping({"", "/"})
    public String summoner(Model model) {
        Summoner summoner = summonerService.getSummoner();
        model.addAttribute("summoner", summoner);

        System.out.println(summoner.toString());

        return "index";
    }
}
