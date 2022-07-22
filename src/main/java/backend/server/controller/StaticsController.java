package backend.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.server.entity.Role;
import backend.server.repository.ICongesRepo;
import backend.server.repository.IUserRepo;
import backend.server.repository.IDetailsRepo;

@RestController
@RequestMapping("/api/v1")
public class StaticsController {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    ICongesRepo congesRepo;
    
    @Autowired
    IDetailsRepo detailsRepo;

    @GetMapping("/statics")
    public void getStatics(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
        
        try {

            long userCount = userRepo.count();
            long congesCount = congesRepo.count();
            long fichesCount = detailsRepo.count();
            // long managerCount = userRepo.countByRole("MANAGER");
            // long employeeCount = userRepo.countByRole("EMPLOYEE");
            // long rhGestCount = userRepo.countByRole("RH_GEST");

            Map<String, Object> statics = new HashMap<>();
            statics.put("userCount", userCount);
            statics.put("congesCount", congesCount);
                statics.put("fichesCount", fichesCount);
            // statics.put("managerCount", managerCount);
            // statics.put("employeeCount", employeeCount);
            // statics.put("rhGestCount", rhGestCount);
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), statics);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
        
    }
}
