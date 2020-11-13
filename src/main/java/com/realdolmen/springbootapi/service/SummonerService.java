package com.realdolmen.springbootapi.service;

import com.realdolmen.springbootapi.http.ApiCaller;
import com.realdolmen.springbootapi.model.Summoner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SummonerService {
    private ApiCaller ApiCaller;

    public Summoner getSummoner() {
        return ApiCaller.requestSummoner();
    }
}
