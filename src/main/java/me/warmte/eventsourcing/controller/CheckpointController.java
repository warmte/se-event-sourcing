package me.warmte.eventsourcing.controller;

import me.warmte.eventsourcing.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> mp = new HashMap<>();
        mp.put("subscriptionId", String.valueOf(subscriptionId));
        mp.put("time", result);
        model.addAttribute("mp", mp);
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
        Map<String, String> mp = new HashMap<>();
        mp.put("subscriptionId", String.valueOf(subscriptionId));
        mp.put("time", result);
        model.addAttribute("mp", mp);
        return "LogoutPage";
    }
}
