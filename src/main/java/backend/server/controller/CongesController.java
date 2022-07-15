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

import backend.server.entity.Conges;
import backend.server.repository.ICongesRepo;
import backend.server.repository.IUserRepo;

@RestController
@RequestMapping("/api/v1")
public class CongesController {

    @Autowired
    ICongesRepo congesRepo;

    @Autowired
    IUserRepo userRepo;

    @PostMapping("/conges")
    public void createConges(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {
        
        String titre = request.getParameter("titre");
        Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateDebut"));
        Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateFin"));
        Long userId = Long.parseLong(request.getParameter("userId"));

        Conges conges = new Conges();
        conges.setTitre(titre);
        conges.setDateDebut(dateDebut);
        conges.setDateFin(dateFin);
        
        try {
            userRepo.findById(userId).ifPresent(conges::setUser);
            congesRepo.save(conges);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Conges créé avec succès");
            map.put("conges", conges);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (Exception e) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", "Erreur lors de la création du congé");
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
