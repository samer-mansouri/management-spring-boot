package backend.server.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.server.entity.Evaluation;
import backend.server.repository.IDetailsRepo;
import backend.server.repository.IEvaluationRepo;

@RestController
@RequestMapping("/api/v1")
public class EvaluationController {

    @Autowired
    IDetailsRepo detailsRepo;

    @Autowired
    IEvaluationRepo evaluationRepo;

    

    @PostMapping("/evaluation")
    public void createEvaluation(HttpServletRequest request, HttpServletResponse response) throws ParseException, StreamWriteException, DatabindException, IOException {

        Double janvierAtteinte = Double.parseDouble(request.getParameter("janvierAtteinte"));
        Double janvierManageriale = Double.parseDouble(request.getParameter("janvierManageriale"));
        Double fevrierAtteinte = Double.parseDouble(request.getParameter("fevrierAtteinte"));
        Double fevrierManageriale = Double.parseDouble(request.getParameter("fevrierManageriale"));
        Double marsAtteinte = Double.parseDouble(request.getParameter("marsAtteinte"));
        Double marsManageriale = Double.parseDouble(request.getParameter("marsManageriale"));
        Double avrilAtteinte = Double.parseDouble(request.getParameter("avrilAtteinte"));
        Double avrilManageriale = Double.parseDouble(request.getParameter("avrilManageriale"));
        Double maiAtteinte = Double.parseDouble(request.getParameter("maiAtteinte"));
        Double maiManageriale = Double.parseDouble(request.getParameter("maiManageriale"));
        Double juinAtteinte = Double.parseDouble(request.getParameter("juinAtteinte"));
        Double juinManageriale = Double.parseDouble(request.getParameter("juinManageriale"));
        Double juilletAtteinte = Double.parseDouble(request.getParameter("juilletAtteinte"));
        Double juilletManageriale = Double.parseDouble(request.getParameter("juilletManageriale"));
        Double aoutAtteinte = Double.parseDouble(request.getParameter("aoutAtteinte"));
        Double aoutManageriale = Double.parseDouble(request.getParameter("aoutManageriale"));
        Double septembreAtteinte = Double.parseDouble(request.getParameter("septembreAtteinte"));
        Double septembreManageriale = Double.parseDouble(request.getParameter("septembreManageriale"));
        Double octobreAtteinte = Double.parseDouble(request.getParameter("octobreAtteinte"));
        Double octobreManageriale = Double.parseDouble(request.getParameter("octobreManageriale"));
        Double novembreAtteinte = Double.parseDouble(request.getParameter("novembreAtteinte"));
        Double novembreManageriale = Double.parseDouble(request.getParameter("novembreManageriale"));
        Double decembreAtteinte = Double.parseDouble(request.getParameter("decembreAtteinte"));
        Double decembreManageriale = Double.parseDouble(request.getParameter("decembreManageriale"));
        Double moyenneAtteinteFinDecembre = Double.parseDouble(request.getParameter("moyenneAtteinteFinDecembre"));
        Double moyenneManagerialeFinDecembre = Double.parseDouble(request.getParameter("moyenneManagerialeFinDecembre"));
        Long id = Long.parseLong(request.getParameter("id"));

        Evaluation evaluation = new Evaluation();
        evaluation.setJanvierAtteinte(janvierAtteinte);
        evaluation.setJanvierManageriale(janvierManageriale);
        evaluation.setFevrierAtteinte(fevrierAtteinte);
        evaluation.setFevrierManageriale(fevrierManageriale);
        evaluation.setMarsAtteinte(marsAtteinte);
        evaluation.setMarsManageriale(marsManageriale);
        evaluation.setAvrilAtteinte(avrilAtteinte);
        evaluation.setAvrilManageriale(avrilManageriale);
        evaluation.setMaiAtteinte(maiAtteinte);
        evaluation.setMaiManageriale(maiManageriale);
        evaluation.setJuinAtteinte(juinAtteinte);
        evaluation.setJuinManageriale(juinManageriale);
        evaluation.setJuilletAtteinte(juilletAtteinte);
        evaluation.setJuilletManageriale(juilletManageriale);
        evaluation.setAoutAtteinte(aoutAtteinte);
        evaluation.setAoutManageriale(aoutManageriale);
        evaluation.setSeptembreAtteinte(septembreAtteinte);
        evaluation.setSeptembreManageriale(septembreManageriale);
        evaluation.setOctobreAtteinte(octobreAtteinte);
        evaluation.setOctobreManageriale(octobreManageriale);
        evaluation.setNovembreAtteinte(novembreAtteinte);
        evaluation.setNovembreManageriale(novembreManageriale);
        evaluation.setDecembreAtteinte(decembreAtteinte);
        evaluation.setDecembreManageriale(decembreManageriale);
        evaluation.setMoyenneAtteinteFinDecembre(moyenneAtteinteFinDecembre);
        evaluation.setMoyenneManagerialeFinDecembre(moyenneManagerialeFinDecembre);
        
        
        try {
            detailsRepo.findById(id).ifPresent(evaluation::setDetails);
            evaluationRepo.save(evaluation);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Evaluation créé avec succès");
            map.put("evaluation", evaluation);
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Evaluation non créé");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(), map);

        }
    }

}
