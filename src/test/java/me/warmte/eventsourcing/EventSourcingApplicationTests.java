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
    public void managerCreateInvalid() {
        Model model = new ExtendedModelMap();
        String page = managerController.createPage("23,59,31,12,20230", model);
        assertThat(page).isEqualTo("ManagerPage");
    }

    @Test
    public void managerCreateValid() {
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
    public void managerProlongValid() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));

        Model model2 = new ExtendedModelMap();
        String page2 = managerController.prolongPage(id, "23,59,31,12,2024", model2);
        assertThat(page2).isEqualTo("ProlongPage");

        Model model3 = new ExtendedModelMap();
        String page3 = managerController.infoPage(id, model3);
        assertThat(page3).isEqualTo("InfoPage");
        assertThat(model3.getAttribute("expiryDate"))
                .isEqualTo(managerService.convert("23,59,31,12,2023").toString() + ", " + managerService.convert("23,59,31,12,2024").toString() + ", ");
    }

    @Test
    public void managerProlongInvalid() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));

        Model model2 = new ExtendedModelMap();
        String page2 = managerController.prolongPage(id, "23,59,31,12,2022", model2);
        assertThat(page2).isEqualTo("ManagerPage");

        Model model3 = new ExtendedModelMap();
        String page3 = managerController.infoPage(id, model3);
        assertThat(page3).isEqualTo("InfoPage");
        assertThat(model3.getAttribute("expiryDate"))
                .isEqualTo(managerService.convert("23,59,31,12,2023").toString() + ", ");
    }

    @Test
    public void checkpointLogin() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));

        Model model2 = new ExtendedModelMap();
        String page2 = checkpointController.loginPage(id, model2);
        assertThat(page2).isEqualTo("LoginPage");
    }

    @Test
    public void checkpointLogoutValid() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));

        Model model2 = new ExtendedModelMap();
        String page2 = checkpointController.loginPage(id, model2);
        assertThat(page2).isEqualTo("LoginPage");

        Model model3 = new ExtendedModelMap();
        String page3 = checkpointController.logoutPage(id, model3);
        assertThat(page3).isEqualTo("LogoutPage");
    }

    @Test
    public void checkpointLogoutInvalid() {
        Model model1 = new ExtendedModelMap();
        String page1 = managerController.createPage("23,59,31,12,2023", model1);
        assertThat(page1).isEqualTo("CreatePage");
        long id = Long.parseLong((String) Objects.requireNonNull(model1.getAttribute("subscriptionId")));

        Model model3 = new ExtendedModelMap();
        String page3 = checkpointController.logoutPage(id, model3);
        assertThat(page3).isEqualTo("CheckpointPage");
    }
}
