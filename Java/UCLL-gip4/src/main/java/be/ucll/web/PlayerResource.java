package be.ucll.web;

import be.ucll.dto.PlayerDTO;
import be.ucll.models.Player;
import be.ucll.dao.PlayerRepository;
import be.ucll.models.Role;
import be.ucll.service.SummonerService;
import be.ucll.service.models.Summoner;
import be.ucll.exceptions.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("player")
public class PlayerResource {

    private SummonerService summonerService;
    private PlayerRepository playerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PlayerResource(SummonerService summonerService, PlayerRepository playerRepository) {
        this.summonerService = summonerService;
        this.playerRepository = playerRepository;
    }

    @ApiOperation("De Summoner/Speler van league of legends creëren) ")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    // De functie wordt aangeroepen door middel van een postrequest. met als input: JSON-object Player: { "leagueName" : "7Stijn7" }
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO player) throws ParameterInvalidException, NotFoundException, AlreadyExistsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName.toString());
        // check of alles ingevult is.
        if (player.getLeagueName() == null || player.getLeagueName().trim().isEmpty()
                || player.getFirstName() == null || player.getFirstName().trim().isEmpty()
                || player.getLastName() == null || player.getLastName().trim().isEmpty()) throw new ParameterInvalidException();

        // Daarna wordt er aan de playerRepository gevraagd of deze speler al gevonden is (op basis van de username), en al in onze databank zit.
        // Zoja, Gooit het een exception: dat de speler al bestaat in ons systeem.
        playerExists(player.getLeagueName());
        //Indien de 'Player' toch nog niet gevonden werd. Wordt de 'summonerService' aangeroepen. Deze service gaat de league of legends api raadplegen.
        // En returnt 'De Summoner' indien het een bestaande username bij league of legends is.
        if(summonerService.getSummoner(player.getLeagueName()).isPresent()){
            Summoner summoner = summonerService.getSummoner(player.getLeagueName()).get();
            // daarna wordt deze 'summoner' omgezet in een 'Player' die dan kan opgeslagen worden in onze databank.
            Player newPlayer = playerRepository.save(new Player.PlayerBuilder()
                    .accountId(summoner.getAccountId())
                    .leagueName(summoner.getName())
                    .firstName(player.getFirstName())
                    .lastName(player.getLastName())
                    .summonerID(summoner.getId())
                    .puuID(summoner.getPuuid())
                    .password(passwordEncoder.encode("test"))
                    .role(Role.PLAYER)
                    .build());
            // Nu returnen we status: 201 created. Omdat onze player succesvol is aangemaakt.
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
        }

        throw new NotFoundException(player.getLeagueName());
    }

    @ApiOperation("Change the player's data")
    // player Updaten
    @PutMapping("{id}")
    public PlayerDTO updatePlayer(@ApiParam(value = "The id of the player where you want to change his data", example = "18", required = true) @PathVariable("id") Long id, @RequestBody PlayerDTO playerDTO) throws HttpClientErrorException, ParameterInvalidException, NotFoundException, AlreadyExistsException, UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player current = playerRepository.findPlayerByLeagueNameIgnoreCase(authentication.getName()).get();
        if(current.getRole().toString()=="PLAYER" && current.getId() != id ){
            throw new UnauthorizedException();
        }
        // check of alles ingevult is.
        if (playerDTO.getLeagueName() == null || playerDTO.getLeagueName().trim().isEmpty()
                || playerDTO.getFirstName() == null || playerDTO.getFirstName().trim().isEmpty()
                || playerDTO.getLastName() == null || playerDTO.getLastName().trim().isEmpty()) throw new ParameterInvalidException();
        if (id <= 0) throw new ParameterInvalidException(id.toString());
        //We gaan controleren of de speler waarvan de leagueName gegeven is, of deze wel bestaat indien deze niet bestaat,
        // laten we zien dat de username niet gevonden is
        if (playerRepository.findPlayerById(id).isPresent()){

            //Wanneer deze speler bestaat, gaan we die in een Player object steken.
            Player player = playerRepository.findPlayerById(id).get();

            //Dan kijken we of ze de league naam van de speler willen veranderen
            if (!player.getLeagueName().equals(playerDTO.getLeagueName())){

                //we gaan controleren of de nieuwe league naam al in onze databank zit, indien dit het geval is, komt er
                //een exception usernameAlreadyExists
                playerExists(playerDTO.getLeagueName());

                //Indien deze er nog niet in steekt, gaan we de gegevens van de speler aan de league api opvragen, als deze
                //de league naam niet kent, gaat er weer een exception komen (van de league api) en die geven we gewoon door
                //aan de gebruiker.
                Summoner summoner = summonerService.getSummoner(playerDTO.getLeagueName()).get();

                //Wanneer de speler wel bestaat in league api gaan we onze player aanpassen naar alle gegevens die we
                // terugkrijgen van de api
                player.setLeagueName(summoner.getName());
                player.setAccountId(summoner.getAccountId());
                player.setSummonerID(summoner.getId());
                player.setPuuID(summoner.getPuuid());
            }
            //controleren of de gebruiker een andere voornaam wilt
            if( !player.getFirstName().equals(playerDTO.getFirstName())){
                player.setFirstName(playerDTO.getFirstName());
            }
            //controleren of de gebruiker een andere achternaam wilt
            if( !player.getLastName().equals(playerDTO.getLastName())){
                player.setLastName(playerDTO.getLastName());
            }

            //spele opslaan in db en de nieuwe speler tonen aan gebruiker.
            playerRepository.save(player);
            return new PlayerDTO(player.getLeagueName(), player.getFirstName(), player.getLastName());
        }

        //Exception om te tonen dat de speler waarvan je iets wilt aanpassen niet bestaat.
        throw new NotFoundException(id.toString());
    }

    @ApiOperation("Get the data from this player")
    @GetMapping("{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@ApiParam(value = "The id of the player where you want to get his data", example = "18", required = true) @PathVariable("id") Long id) throws NotFoundException, ParameterInvalidException {
        if (id <= 0) throw new ParameterInvalidException(id.toString());

        //controleren of speler in onze db bestaat
        if(playerRepository.findPlayerById(id).isPresent()) {
            //speler opvragen en teruggeven
            Player player = playerRepository.findPlayerById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body((new PlayerDTO(player.getLeagueName(), player.getFirstName(), player.getLastName())));
        }
        throw new NotFoundException(id.toString());
    }

    @ApiOperation("Delete a player")
    @DeleteMapping("{id}")
    public ResponseEntity deletePlayer(@ApiParam(value = "The id of the player where you want to delete him", example = "18", required = true) @PathVariable("id") Long id) throws NotFoundException, ParameterInvalidException, UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Player current = playerRepository.findPlayerByLeagueNameIgnoreCase(authentication.getName()).get();
        if(current.getRole().toString()=="PLAYER"){
            throw new UnauthorizedException();
        }
        if (id <= 0) throw new ParameterInvalidException(id.toString());

        //We gaan controleren of de speler waarvan de leagueName gegeven is, of deze wel bestaat in onze db
        if(playerRepository.findPlayerById(id).isPresent()) {

            //Zo ja, dan verwijderen we deze
            playerRepository.delete(playerRepository.findPlayerById(id).get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        //zo niet => exception
        throw new NotFoundException(id.toString());
    }

    //Helper methode om te controleren of de league naam reeds in onze db steekt
    public void playerExists(String leagueName) throws AlreadyExistsException{
        if(playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()){
            throw new AlreadyExistsException(leagueName);
        }
    }


}
