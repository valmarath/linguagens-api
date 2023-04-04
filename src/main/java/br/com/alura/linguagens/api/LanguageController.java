package br.com.alura.linguagens.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class LanguageController {
    
    //AvzPLiSfotEiOKuD
    @Autowired
    private LanguageRepository repository;


    @GetMapping(value="/languages")
    public List<Language> getLanguages() {
        List<Language> languages = repository.findByOrderByRanking();
        return languages;
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> registerLanguage(@RequestBody Language language) {
        Language savedLanguage = repository.save(language);
        return new ResponseEntity<>(savedLanguage, HttpStatus.CREATED);
    }

    @GetMapping("/languages/{id}")
    public Language getLanguageById(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/languages/{id}")
    public Language updateLanguage(@PathVariable String id, @RequestBody Language language) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        language.setId(id);
        Language updatedLanguage = repository.save(language);
        return updatedLanguage;
    }

    @DeleteMapping("/languages/{id}")
    public void deleteLanguage(@PathVariable String id) {
        repository.deleteById(id);
    }
}
