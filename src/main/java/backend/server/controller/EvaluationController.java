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

        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        String userId = request.getParameter("userId");

        Evaluation evaluation = new Evaluation();
        // evaluation.setTitre(titre);
        // evaluation.setDescription(description);
        // evaluation.setDate(date);
        
        try {
            detailsRepo.findById(Long.parseLong(userId)).ifPresent(evaluation::setDetails);
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
