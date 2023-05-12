package me.warmte.eventsourcing.controller;

import me.warmte.eventsourcing.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/manager")
    public String managerPage() {
        return "ManagerPage";
    }

    @GetMapping("/manager/info")
    public String infoPage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        String result = managerService.getSubscriptionInfo(subscriptionId);
        if (result == null) {
            return "ManagerPage";
        }
        model.addAttribute("subscriptionId", String.valueOf(subscriptionId));
        model.addAttribute("expiryDate", result);
        return "InfoPage";
    }

    @GetMapping("/manager/create")
    public String createPage(
            @RequestParam(name = "t") String expiry,
            Model model
    ) {
        String result = managerService.createSubscription(expiry);
        if (result == null) {
            return "ManagerPage";
        }
        model.addAttribute("subscriptionId", result);
        model.addAttribute("expiryDate", expiry);
        return "CreatePage";
    }

    @GetMapping("/manager/prolong")
    public String prolongPage(
            @RequestParam(name = "id") long subscriptionId,
            @RequestParam(name = "t") String expiry,
            Model model
    ) {
        String result = managerService.prolongSubscription(subscriptionId, expiry);
        if (result == null) {
            return "ManagerPage";
        }
        model.addAttribute("subscriptionId", String.valueOf(subscriptionId));
        model.addAttribute("expiryDate", result);
        return "ProlongPage";
    }
}
