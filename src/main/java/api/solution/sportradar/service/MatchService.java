package api.solution.sportradar.service;

import api.solution.sportradar.model.Match;

import java.util.List;

public interface MatchService {

    List<Match> getAll();

    List<Match> getCompleted();

    List<Match> getLive();

    List<Match> getForTeam(String teamName);

}
