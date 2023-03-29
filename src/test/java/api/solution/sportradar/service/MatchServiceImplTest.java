package api.solution.sportradar.service;

import api.solution.sportradar.data.DataProvider;
import api.solution.sportradar.model.Match;
import api.solution.sportradar.model.MatchStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceImplTest {

    @Mock
    private DataProvider dataProvider;

    private MatchServiceImpl matchService;

    @BeforeEach
    public void setUp() {
        matchService = new MatchServiceImpl(dataProvider);
    }

    @Test
    public void testGetAll() {
        List<Match> matches = new ArrayList<>();
        Match match1 = new Match.Builder().build();
        Match match2 = new Match.Builder().build();
        matches.add(match1);
        matches.add(match2);

        when(dataProvider.getMatches()).thenReturn(matches);

        List<Match> result = matchService.getAll();
        assertEquals(matches, result);
    }

    @Test
    public void testGetCompleted() {
        List<Match> matches = new ArrayList<>();
        Match match1 = new Match.Builder().withMatchStatus(MatchStatus.COMPLETED).build();
        Match match2 = new Match.Builder().withMatchStatus(MatchStatus.COMPLETED).build();
        Match match3 = new Match.Builder().withMatchStatus(MatchStatus.LIVE).build();
        Match match4 = new Match.Builder().withMatchStatus(MatchStatus.SCHEDULED).build();

        matches.add(match1);
        matches.add(match2);
        matches.add(match3);
        matches.add(match4);

        when(dataProvider.getMatches()).thenReturn(matches);

        List<Match> result = matchService.getCompleted();
        assertEquals(2, result.size());
        assertEquals(match1, result.get(0));
    }

    @Test
    public void testGetLive() {
        List<Match> matches = new ArrayList<>();
        Match match1 = new Match.Builder().withMatchStatus(MatchStatus.COMPLETED).build();
        Match match2 = new Match.Builder().withMatchStatus(MatchStatus.LIVE).build();
        Match match3 = new Match.Builder().withMatchStatus(MatchStatus.LIVE).build();
        Match match4 = new Match.Builder().withMatchStatus(MatchStatus.SCHEDULED).build();

        matches.add(match1);
        matches.add(match2);
        matches.add(match3);
        matches.add(match4);

        when(dataProvider.getMatches()).thenReturn(matches);

        List<Match> result = matchService.getLive();
        assertEquals(2, result.size());
        assertEquals(match2, result.get(0));
    }

    @Test
    public void testGetForTeam() {
        List<Match> matches = new ArrayList<>();
        Match match1 = new Match.Builder().withAwayTeam("Denver Nuggets").withHomeTeam("Dallas Mavericks").build();
        Match match2 = new Match.Builder().withAwayTeam("Milwaukee Bucks").withHomeTeam("Miami Heat").build();
        Match match3 = new Match.Builder().withHomeTeam("Milwaukee Bucks").withAwayTeam("Portland Trail Blazers").build();
        Match match4 = new Match.Builder().withHomeTeam("Oklahoma City Thunder").withAwayTeam("Sacramento Kings").build();

        matches.add(match1);
        matches.add(match2);
        matches.add(match3);
        matches.add(match4);

        when(dataProvider.getMatches()).thenReturn(matches);

        List<Match> result1 = matchService.getForTeam("Nuggets");
        List<Match> result2 = matchService.getForTeam("Denver");
        List<Match> result3 = matchService.getForTeam("Bucks");
        List<Match> result4 = matchService.getForTeam("Oklahoma City");
        List<Match> result5 = matchService.getForTeam("team");


        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
        assertEquals(2, result3.size());
        assertEquals(1, result4.size());
        assertEquals(0, result5.size());

    }

}
