package be.ucll.web;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
import be.ucll.exceptions.AlreadyExistsException;
import be.ucll.exceptions.NotFoundException;
import be.ucll.exceptions.ParameterInvalidException;
import be.ucll.models.Team;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamResource {

    private TeamRepository teamRepository;
    @Autowired
    public TeamResource(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Operation(
            summary = "Create new Team",
            description = "Provide a team name and create a team"
    )
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) throws ParameterInvalidException, AlreadyExistsException {
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new ParameterInvalidException();

        if (teamRepository.findTeamByNameIgnoreCase(teamDTO.getName()).isPresent()) throw new AlreadyExistsException(teamDTO.getName());

        Team newTeam = teamRepository.save(new Team.TeamBuilder()
                .name(teamDTO.getName())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);
    }

    @Operation(
            summary = "Update a Team",
            description = "Change the Team name based on an existing team ID"
    )
    // team Updaten
    @PutMapping  ("/{id}")
    @PreAuthorize("hasRole('MANAGER')")// localhost:8080/team?id=
        public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO) throws ParameterInvalidException, NotFoundException, AlreadyExistsException {
        Team team;
        if (id <= 0) throw new ParameterInvalidException(id.toString());
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new ParameterInvalidException(teamDTO.getName());
        if (teamRepository.findById(id).isPresent()){
             team = teamRepository.findById(id).get();
            if (!team.getName().equals(teamDTO.getName())){
                teamALreadyExists(teamDTO.getName());
                team.setName(teamDTO.getName());
            }
        }else{
            throw new NotFoundException(teamDTO.getName());
        }

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @Operation(
            summary = "Get team by id",
            description = "get a team based on an existing team id"
    )
     @GetMapping("/{id}") // teamname veranderen naar id
     @PreAuthorize("hasRole('MANAGER')")
        public ResponseEntity<TeamDTO> getTeam(@PathVariable("id") Long id) throws NotFoundException, ParameterInvalidException {
            if (id <= 0) throw new ParameterInvalidException (id.toString());
            //controleren of team in onze db bestaat
            if(teamRepository.findById(id).isPresent()) {
                //team opvragen en teruggeven
                Team team = teamRepository.findById(id).get();
                return ResponseEntity.status(HttpStatus.OK).body(new TeamDTO(team.getName()));
            }
            throw new NotFoundException(id.toString());
        }

    @Operation(
            summary = "Delete team by id",
            description = "Delete a team based on an existing team id"
    )
    @DeleteMapping ("/{id}")// id veranderd
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity deleteTeam(@PathVariable("id") Long id) throws NotFoundException, ParameterInvalidException {
        if (id <= 0) throw new ParameterInvalidException(id.toString());

        if(teamRepository.findById(id).isPresent()) {
            Team t = teamRepository.findById(id).get();
            teamRepository.delete(t);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        throw new NotFoundException(id.toString());
    }

    private void teamALreadyExists(String teamName) throws AlreadyExistsException{
        if (teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()){
            throw new AlreadyExistsException(teamName);
        }
    }

}

