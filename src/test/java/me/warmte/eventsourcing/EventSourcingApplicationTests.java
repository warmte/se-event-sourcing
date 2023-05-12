package me.warmte.eventsourcing;

import me.warmte.eventsourcing.controller.CheckpointController;
import me.warmte.eventsourcing.controller.ManagerController;
import me.warmte.eventsourcing.controller.ReportingController;
import me.warmte.eventsourcing.service.CheckpointService;
import me.warmte.eventsourcing.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventSourcingApplicationTests {

    @Autowired
    private CheckpointController checkpointController;
    @Autowired
    private ManagerController managerController;
    @Autowired
    private ReportingController reportingController;

    @Autowired
    private ManagerService managerService;
    @Autowired
    private CheckpointService checkpointService;

    @Test
    public void invalid_manager_create() {
        Model model = new ExtendedModelMap();
        String page = managerController.createPage("23,59,31,12,20230", model);
        assertThat(page).isEqualTo("ManagerPage");
    }

    @Test
    public void valid_manager_create() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));
        Model model2 = new ExtendedModelMap();
        String page2 = managerController.infoPage(id, model2);
        assertThat(page2).isEqualTo("InfoPage");
        assertThat(model2.getAttribute("expiryDate"))
                .isEqualTo(managerService.convert("23,59,31,12,2023").toString() + ", ");
    }

    @Test
    public void manager_info() {
    }

    @Test
    public void manager_prolong() {
    }

    @Test
    public void checkpoint_login() {
    }

    @Test
    public void checkpoint_logout() {
    }
}
