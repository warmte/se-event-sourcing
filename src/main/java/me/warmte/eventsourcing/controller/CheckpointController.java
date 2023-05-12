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
    private CheckpointService checkpointService;

    @GetMapping("/checkpoint")
    public String checkpointPage() {
        return "CheckpointPage";
    }

    @GetMapping("/checkpoint/login")
    public String loginPage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        String result = checkpointService.login(subscriptionId);
        if (result == null) {
            return "CheckpointPage";
        }
        model.addAttribute("subscriptionId", String.valueOf(subscriptionId));
        model.addAttribute("time", result);
        return "LoginPage";
    }

    @GetMapping("/checkpoint/logout")
    public String logoutPage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        String result = checkpointService.logout(subscriptionId);
        if (result == null) {
            return "CheckpointPage";
        }
        model.addAttribute("subscriptionId", String.valueOf(subscriptionId));
        model.addAttribute("time", result);
        return "LogoutPage";
    }
}
