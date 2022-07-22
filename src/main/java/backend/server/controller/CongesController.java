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
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.server.entity.Conges;
import backend.server.repository.ICongesRepo;
import backend.server.repository.IUserRepo;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CongesController {

    @Autowired
    ICongesRepo congesRepo;

    @Autowired
    IUserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/conges")
    @Transactional
    public void createConges(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        
        String titre = request.getParameter("titre");
        Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateDebut"));
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateFin"));
        Long userId = Long.parseLong(request.getParameter("userId"));

        log.info("Creating conges with titre: " + titre + " and dateDebut: " + dateDebut + " and dateFin: " + dateFin + " and userId: " + userId);

        

        Conges conges = new Conges();
        conges.setTitre(titre);
        conges.setDateDebut(dateDebut);
        conges.setDateFin(dateFin);
   

        //PERSIST conges
        try {
            Conges newConges = userRepo.findById(userId).
                    map(user -> {
                        conges.setUser(user);
                        entityManager.persist(conges);
                        return congesRepo.save(conges);
                    }).orElseThrow(() -> new RuntimeException("User not found"));
            //log.info("Conges created with id: " + conges.getId());
            //congesRepo.save(conges);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Conges créé avec succès");
            map.put("conges", newConges);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (Exception e) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }

    
    }

    @GetMapping("/conges")
    public void getConges(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        Long userId = Long.parseLong(request.getParameter("userId"));
        try {
            List<Conges> conges = congesRepo.findAllByUserId(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Conges trouvés");
            map.put("conges", conges);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
            
        } catch (Exception e) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

    @GetMapping("/conges/all")
    public void getAllConges(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        try {
            List<Conges> conges = (List<Conges>) congesRepo.findAll();
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Conges trouvés");
            map.put("conges", conges);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
            
        } catch (Exception e) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }
}
