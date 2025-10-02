package vanktesh.example.Money_Manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status", "/health"})
public class HomeController {
    //    end point to check that application is running successfully
    @GetMapping
    public String healthCheck(){
        return "Application is running";
    }
}