package be.ucll.java.gip5.dao;

import be.ucll.java.gip5.model.Persoon;
import be.ucll.java.gip5.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersoonRepository extends JpaRepository<Persoon, Long> {
    Optional<Persoon> findPersoonById(Long id);
    List<Persoon> findAll();
    List<Persoon> findAllByVoornaamContaining(String voornaam);
    List<Persoon> findAllByVoornaamContainingIgnoreCase(String voornaam);
    Optional<List<Persoon>> findAllByNaamContaining(String voornaam);
    Optional<List<Persoon>> findAllByNaamContainingIgnoreCase(String voornaam);
    Optional<List<Persoon>> findAllByNaamContainingIgnoreCaseOrVoornaamContainingIgnoreCase(String searchterm, String searchterm2);
    Optional<List<Persoon>> findAllByGeslacht(String geslacht);
    Optional<List<Persoon>> findAllByAdresContaining(String adres);
    Optional<Persoon> findPersoonByEmailAndWachtwoord(String email, String wachtwoord);
    Optional<Persoon> findPersoonByEmailIgnoreCase(String email);
    Optional<Persoon> findPersoonByApi(String api);
}
