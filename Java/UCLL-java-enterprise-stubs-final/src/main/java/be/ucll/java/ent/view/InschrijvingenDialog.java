package be.ucll.java.ent.view;

import be.ucll.java.ent.controller.InschrijvingController;
import be.ucll.java.ent.controller.LeermoduleController;
import be.ucll.java.ent.domain.InschrijvingDTO;
import be.ucll.java.ent.domain.LeermoduleDTO;
import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.utils.BeanUtil;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InschrijvingenDialog extends Dialog {
    private Logger logger = LoggerFactory.getLogger(InschrijvingenDialog.class);

    // Spring beans/services
    private InschrijvingController inschrijvingMgr;
    private LeermoduleController leermoduleMgr;

    private Label lblStudInfo;
    private ListBox<InschrijvingDTO> lstInsch;
    private Button btnUitschrijven;

    private Html hruler;

    private HorizontalLayout hl1;
    private ComboBox<LeermoduleDTO> cmbLeermodules;
    private Checkbox chkBetaald;
    private Button btnInschrijven;

    private LeermoduleDTO selectedLM;
    private InschrijvingDTO selectedInschr;

    public InschrijvingenDialog(StudentDTO s) {
        super();

        // Load Spring beans
        inschrijvingMgr = BeanUtil.getBean(InschrijvingController.class);
        leermoduleMgr = BeanUtil.getBean(LeermoduleController.class);

        lblStudInfo = new Label("Inschrijvingen voor student: " + s.getVoornaam() + " " + s.getNaam() + " (" + s.getGeboortedatumstr() + ")");
        lblStudInfo.setId("bold-label");
        add(lblStudInfo);

        lstInsch = new ListBox<>();
        List<InschrijvingDTO> inschrijvingen = inschrijvingMgr.getInschrijvingenVoorStudentId(s.getId());
        if (inschrijvingen != null && inschrijvingen.size() > 0) {
            lstInsch.setItems(inschrijvingen);
        }
        lstInsch.setMaxHeight("50%");
        lstInsch.addValueChangeListener(e -> {
            selectedInschr = e.getValue();
            btnUitschrijven.setVisible(true);
        });
        add(lstInsch);

        btnUitschrijven = new Button("Uitschrijven");
        btnUitschrijven.setVisible(false);
        btnUitschrijven.addClickListener(e -> {
            try {
                inschrijvingMgr.deleteInschrijving(s, leermoduleMgr.getLeermoduleByCode(selectedInschr.getCode()));
                lstInsch.setItems(inschrijvingMgr.getInschrijvingenVoorStudentId(s.getId()));
                Notification.show("Student uitgeschreven", 3000, Notification.Position.TOP_CENTER);
                //this.close();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                Notification.show(ex.getMessage(), 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        add(btnUitschrijven);

        hruler = new Html("<span><br/><hr/><br/></span>");
        add(hruler);

        hl1 = new HorizontalLayout();
        hl1.setWidthFull();
        cmbLeermodules = new ComboBox<>();
        cmbLeermodules.setItemLabelGenerator(LeermoduleDTO::toString);
        cmbLeermodules.setItems(leermoduleMgr.getAllLeermodules());
        cmbLeermodules.setWidth("500px");
        cmbLeermodules.addValueChangeListener(event -> {
            selectedLM = cmbLeermodules.getValue();
            btnInschrijven.setEnabled(true);
        });

        chkBetaald = new Checkbox("Betaald");

        hl1.add(new Label("Leermodules: "), cmbLeermodules, chkBetaald);
        add(hl1);

        btnInschrijven = new Button("Inschrijven");
        btnInschrijven.setEnabled(false);
        btnInschrijven.addClickListener(e -> {
            try {
                inschrijvingMgr.createInschrijving(s, selectedLM, chkBetaald.getValue());
                lstInsch.setItems(inschrijvingMgr.getInschrijvingenVoorStudentId(s.getId()));
                Notification.show("Student ingeschreven", 3000, Notification.Position.TOP_CENTER);
                chkBetaald.setValue(false);
                //this.close();
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        add(btnInschrijven);
    }

}
