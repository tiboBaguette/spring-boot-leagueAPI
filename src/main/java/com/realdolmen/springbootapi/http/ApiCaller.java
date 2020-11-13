package com.realdolmen.springbootapi.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.springbootapi.model.Summoner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiCaller {
    private final RestTemplate restTemplate;
    private final Summoner summoner;

    private final String playerName = "zeefiosta3";
    // keys expire after 1day get a new kay at: https://developer.riotgames.com/
    private final String APIKey = "RGAPI-0069a40c-92ad-4999-ada9-914087a289e6";

    public ApiCaller(RestTemplateBuilder rtb) {
        this.restTemplate = rtb.build();
        this.summoner = new Summoner();
    }

    public Summoner requestSummoner() {
        setSummonerDetails();
        setSummonerLeague();
        setSummonerMasteryScore();

        return this.summoner;
    }

    // documentation: https://developer.riotgames.com/apis#summoner-v4/GET_getBySummonerName
    private void setSummonerDetails() {
        String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + playerName + "?api_key=" + APIKey;
        String summonerJson = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(summonerJson);

            // set id
            String id = jsonNode.get("id").asText();
            summoner.setId(id);
            // set accountId
            String accountId = jsonNode.get("accountId").asText();
            summoner.setAccountId(accountId);
            // set puuid
            String puuid = jsonNode.get("puuid").asText();
            summoner.setPuuid(puuid);
            // set name
            String summonerName = jsonNode.get("name").asText();
            summoner.setName(summonerName);
            // set profileIconId
            int profileIconId = jsonNode.get("profileIconId").asInt();
            summoner.setProfileIconId(profileIconId);
            // set revisionDate
            long revisionDate = jsonNode.get("revisionDate").asLong();
            summoner.setRevisionDate(revisionDate);
            // set summonerLevel
            long summonerLevel = jsonNode.get("summonerLevel").asLong();
            summoner.setSummonerLevel(summonerLevel);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // documentation: https://developer.riotgames.com/apis#league-v4/GET_getLeagueEntriesForSummoner
    private void setSummonerLeague() {
        String url = "https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summoner.getId() + "?api_key=" + APIKey;
        String leagueJson = restTemplate.getForObject(url, String.class, 1);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(leagueJson);

            for (int i = 0; i < jsonNode.size(); i++) {
                // set queueType
                String queueType = jsonNode.get(i).get("queueType").asText();
                summoner.setQueueType(queueType);
                // set tier
                String tier = jsonNode.get(i).get("tier").asText();
                summoner.setTier(tier);
                // set rank
                String rank = jsonNode.get(i).get("rank").asText();
                summoner.setRank(rank);
                // set leaguePoints
                int leaguePoints = jsonNode.get(i).get("leaguePoints").asInt();
                summoner.setLeaguePoints(leaguePoints);
                // set wins
                int wins = jsonNode.get(i).get("wins").asInt();
                summoner.setWins(wins);
                // set leaguePoints
                int losses = jsonNode.get(i).get("losses").asInt();
                summoner.setLosses(losses);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // documentation: https://developer.riotgames.com/apis#champion-mastery-v4/GET_getChampionMasteryScore
    private void setSummonerMasteryScore() {
        String url = "https://euw1.api.riotgames.com/lol/champion-mastery/v4/scores/by-summoner/" + summoner.getId() + "?api_key=" + APIKey;
        String masteryJson = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(masteryJson);

            // set id
            int masteryScore = jsonNode.asInt();
            summoner.setMasteryScore(masteryScore);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
