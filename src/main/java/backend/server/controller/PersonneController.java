package backend.server.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.server.entity.Personne;
import backend.server.entity.Enums.Relation;
import backend.server.entity.Enums.Sexe;
import backend.server.repository.IPersonneRepo;
import backend.server.repository.IUserRepo;

@RestController
@RequestMapping("/api/v1")
public class PersonneController {

    @Autowired
    IPersonneRepo personneRepo;

    @Autowired
    IUserRepo userRepo;

    @PostMapping("/personne")
    public void createFamilyMember(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateNaissance"));
        Sexe sexe = Sexe.valueOf(request.getParameter("sexe"));
        Relation relation = Relation.valueOf(request.getParameter("relation"));
        Long userId = Long.parseLong(request.getParameter("userId"));

        Personne personne = new Personne();
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setDateNaissance(dateNaissance);
        personne.setSexe(sexe);
        personne.setRelation(relation);
        try {
            userRepo.findById(userId).ifPresent(personne::setUser);
            personneRepo.save(personne);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Personne créé avec succès");
            map.put("personne", personne);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Erreur lors de la création de la personne");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    
    }

    @GetMapping("/personne")
    public void getFamilyMember(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("userId"));
            List<Personne> personne = personneRepo.findAllByUserId(id);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Personne trouvée avec succès");
            map.put("personnes", personne);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Erreur lors de la récupération de la personne");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

    
    
}
