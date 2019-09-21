package com.mexpedition.web.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exception.ExpeditionIntrouvableException;

@RestController
public class ExpeditionController {
	
	@Autowired
    private ExpeditionDao expeditionDao;
	
    //ajouter une expedition
    @PostMapping(value = "/Expeditions")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Expedition expedition) {
    	Expedition expeditionAdded =  expeditionDao.save(expedition);
        if (expeditionAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expeditionAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    
    @GetMapping(value = "/Expeditions/{id}")
    public Expedition GetExpeditionByID(@PathVariable int id) {
    	Expedition expedition = expeditionDao.findById(id);
        if(expedition==null) 
        	throw new ExpeditionIntrouvableException("L'epédition avec l'id " + id + " est INTROUVABLE.");
        return expedition;
    }
    
    @PutMapping (value = "/Expeditions")
    public void updateProduit(@RequestBody Expedition expedition) {

    	expeditionDao.save(expedition);
    }
    
    @DeleteMapping (value = "/Expeditions/{id}")
    public void supprimerProduit(@PathVariable int id) {

    	expeditionDao.deleteById(id);
    }
    
    //Récupérer la liste des produits
    @RequestMapping(value = "/Expeditions", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Expedition> expeditions = expeditionDao.findAll();
        if(expeditions==null) 
        	throw new ExpeditionIntrouvableException("Aucune expedition trouvée");
        MappingJacksonValue expeditionsMapping = new MappingJacksonValue(expeditions);
        return expeditionsMapping;
    }
}
