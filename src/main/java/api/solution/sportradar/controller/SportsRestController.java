package api.solution.sportradar.controller;

import api.solution.sportradar.model.dto.MatchDTO;
import api.solution.sportradar.service.MatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Sports", description = "SportsRestController")
@RestController("/match")
public class SportsRestController {

    private final MatchService matchService;

    private final ModelMapper modelMapper;

    public SportsRestController(MatchService matchService, ModelMapper modelMapper) {
        this.matchService = matchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MatchDTO> getAllAvailableMatches() {
        return matchService.getAll().stream().map(entity -> modelMapper.map(entity, MatchDTO.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/completed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MatchDTO> getCompletedMatches() {
        return matchService.getCompleted().stream().map(entity -> modelMapper.map(entity, MatchDTO.class)).collect(Collectors.toList());
    }

    @GetMapping(value="/live", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MatchDTO> getLiveMatches() {
        return matchService.getLive().stream().map(entity -> modelMapper.map(entity, MatchDTO.class)).collect(Collectors.toList());
    }

    @GetMapping(value="/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MatchDTO> filterByTeam(@RequestParam String teamName) {
        return matchService.getForTeam(teamName).stream().map(entity -> modelMapper.map(entity, MatchDTO.class)).collect(Collectors.toList());
    }

}
