package me.warmte.eventsourcing.controller;

import me.warmte.eventsourcing.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckpointController {

    @Autowired
    private CheckpointService checkPointService;

    @GetMapping("/checkpoint")
    public String checkpointPage() {
        return "CheckpointPage";
    }

    @GetMapping("/checkpoint/login")
    public String loginPage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        model.addAttribute("subscriptionId", subscriptionId);
        return "LoginPage";
    }

    @GetMapping("/checkpoint/logout")
    public String logoutPage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        model.addAttribute("subscriptionId", subscriptionId);
        return "LogoutPage";
    }
}
