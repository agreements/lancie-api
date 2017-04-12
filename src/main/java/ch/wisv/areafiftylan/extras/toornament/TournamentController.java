package ch.wisv.areafiftylan.extras.toornament;

import ch.wisv.toornament.model.Tournament;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/tournaments/remote")
    public List<Tournament> getRemoteTournaments(){
        return tournamentService.getRemoteTournaments();
    }
}
