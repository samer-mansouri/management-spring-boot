package backend.server.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.server.entity.Details;
import backend.server.entity.User;
import backend.server.entity.Enums.Periodicite;
import backend.server.entity.Enums.RepartitionPoste;
import backend.server.entity.Enums.Statut;
import backend.server.repository.IDetailsRepo;
import backend.server.repository.IUserRepo;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DetailsController {

    @Autowired
    IDetailsRepo detailsRepo;

    @Autowired
    IUserRepo userRepo;


    @GetMapping("/details_all")
    public ResponseEntity<List<Details>> getAllDetails() {
        try {
            List<Details> list = detailsRepo.findAll();
            if(list.isEmpty() || list.size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return  new ResponseEntity<List<Details>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/details")
    public void createDetail(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException{
        try {
            String fonction = request.getParameter("fonction");
            Date dateEmbauche = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateEmbauche"));
            Integer anciennete = Integer.parseInt(request.getParameter("anciennete"));
            Integer intervalle = Integer.parseInt(request.getParameter("intervalle"));
            String departement = request.getParameter("departement");
            Periodicite periodcitePrime = Periodicite.valueOf(request.getParameter("periodcitePrime"));
            String intituleService = request.getParameter("intituleService");
            Statut statut = Statut.valueOf(request.getParameter("statut"));
            RepartitionPoste repartitionPoste = RepartitionPoste.valueOf(request.getParameter("repartitionPoste"));
            Long userId = Long.parseLong(request.getParameter("userId"));
            Long managerId = Long.parseLong(request.getParameter("managerId"));
            //log.info("Creating detail: " + fonction + " " + dateEmbauche + " " + anciennete + " " + intervalle + " " + departement + " " + periodcitePrime + " " + intituleService + " " + statut + " " + repartitionPoste);
            Details details = new Details();
            details.setFonction(fonction);
            details.setDateEmbauche(dateEmbauche);
            details.setAnciennete(anciennete);
            details.setIntervalle(intervalle);
            details.setDepartement(departement);
            details.setPeriodicitePrime(periodcitePrime);
            details.setIntituleService(intituleService);
            details.setStatut(statut);
            details.setRepartitionPoste(repartitionPoste);
            //details.setUser(userRepo.findById(userId).get());
            //detailsRepo.save(details);
            //log.info("Created detail: " + details);
            try {
                userRepo.findById(userId).ifPresent(details::setUser);
                userRepo.findById(managerId).ifPresent(details::setManager);
                log.info("Created detail: " + details);
                detailsRepo.save(details);
                Map<String, String> map = new HashMap<>();
                map.put("message", "Details created successfully");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), map);
            } catch (Exception e) {
                log.error("User not found");
                Map<String, String> map = new HashMap<>();
                map.put("message", e.getMessage());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                new ObjectMapper().writeValue(response.getOutputStream(), map);
                
            }
        } catch (Exception e) {
            log.error("Error creating detail");
            Map<String, String> map = new HashMap<>();
            map.put("message", "Error creating detail");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

    @GetMapping("/details")
    public void getDetails(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException{
        try {
            log.info("Getting details", request.getParameter("userId"));
            Long userId = Long.parseLong(request.getParameter("userId"));
            log.info("Getting details for user: " + userId);
            Details details = detailsRepo.findByUserId(userId);
            log.info("Found details: " + details);
            if (details != null) {
                log.info("Found details: " + details);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), details);
            } else {
                log.info("No details found");
                Map<String, String> map = new HashMap<>();
                map.put("message", "No details found");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                new ObjectMapper().writeValue(response.getOutputStream(), map);
            }
        } catch (Exception e) {
            log.error("Error getting details");
            Map<String, String> map = new HashMap<>();
            map.put("message", "Error getting details");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

    @PutMapping("/details")
    public void updateDetails(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException{
        try {
            log.info("Updating details", request.getParameter("userId"));
            Long userId = Long.parseLong(request.getParameter("userId"));
            log.info("Updating details for user: " + userId);
            Details details = detailsRepo.findByUserId(userId);
            log.info("Found details: " + details);
            if (details != null) {
                log.info("Found details: " + details);
                String fonction = request.getParameter("fonction");
                Date dateEmbauche = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateEmbauche"));
                Integer anciennete = Integer.parseInt(request.getParameter("anciennete"));
                Integer intervalle = Integer.parseInt(request.getParameter("intervalle"));
                String departement = request.getParameter("departement");
                Periodicite periodcitePrime = Periodicite.valueOf(request.getParameter("periodcitePrime"));
                String intituleService = request.getParameter("intituleService");
                Statut statut = Statut.valueOf(request.getParameter("statut"));
                RepartitionPoste repartitionPoste = RepartitionPoste.valueOf(request.getParameter("repartitionPoste"));
                Long managerId = Long.parseLong(request.getParameter("managerId"));
                details.setFonction(fonction);
                details.setDateEmbauche(dateEmbauche);
                details.setAnciennete(anciennete);
                details.setIntervalle(intervalle);
                details.setDepartement(departement);
                details.setPeriodicitePrime(periodcitePrime);
                details.setIntituleService(intituleService);
                details.setStatut(statut);
                details.setRepartitionPoste(repartitionPoste);
                details.setUser(userRepo.findById(userId).get());
                details.setManager(userRepo.findById(managerId).get());
                log.info("Updated detail: " + details);
                detailsRepo.save(details);
                Map<String, String> map = new HashMap<>();
                map.put("message", "Details updated successfully");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), map);
            } else {
                log.info("No details found");
                Map<String, String> map = new HashMap<>();
                map.put("message", "No details found");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                new ObjectMapper().writeValue(response.getOutputStream(), map);
            }
        } catch (Exception e) {
            log.error("Error updating details");
            Map<String, String> map = new HashMap<>();
            map.put("message", "Error updating details");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
    }

   

}
