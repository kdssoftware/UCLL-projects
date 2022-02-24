package be.ucll.java.gip5.view;

import be.ucll.java.gip5.dao.PersoonRepository;
import be.ucll.java.gip5.dto.PersoonDTO;
import be.ucll.java.gip5.exceptions.InvalidCredentialsException;
import be.ucll.java.gip5.exceptions.NotFoundException;
import be.ucll.java.gip5.exceptions.ParameterInvalidException;
import be.ucll.java.gip5.model.Persoon;
import be.ucll.java.gip5.rest.PersoonResource;
import be.ucll.java.gip5.util.BeanUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@UIScope
public class PersoonView extends VerticalLayout {

    // Spring controllers
    private final PersoonResource persoonResource;
    private PersoonRepository persoonRepository;
    private final MessageSource msgSource;

    private SplitLayout splitLayout;
    private VerticalLayout lpvLayout; // Left Panel Vertical Layout
    private HorizontalLayout lphLayout;
    private VerticalLayout rpvLayout; // Right Panel Vertical Layout
    private HorizontalLayout rphLayout;
    private PersoonFragment frm;

    private Label lblNaam;
    private TextField txtNaam;

    private Grid<Persoon> grid;

    private Button btnCancel;
    private Button btnCreate;
    private Button btnUpdate;
    private Button btnDelete;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PersoonView() {
        super();

        persoonRepository = BeanUtil.getBean(PersoonRepository.class);
        persoonResource = BeanUtil.getBean(PersoonResource.class);
        persoonResource.setLocale(VaadinSession.getCurrent().getLocale());
        msgSource = BeanUtil.getBean(MessageSource.class);

        this.setSizeFull();
        this.setPadding(false);

        splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(createGridLayout());
        splitLayout.addToSecondary(createEditorLayout());
        add(splitLayout);
    }


    private Component createGridLayout() {
        lpvLayout = new VerticalLayout();
        lpvLayout.setWidthFull();

        lphLayout = new HorizontalLayout();
        lblNaam = new Label("search"); //new Label(msgSource.getMessage("persoonresource.lblNaam", null, getLocale()));
        txtNaam = new TextField();
        txtNaam.setValueChangeMode(ValueChangeMode.EAGER);
        txtNaam.addValueChangeListener(e -> {
            try {
                handleClickSearch(null);
            } catch (NotFoundException notFoundException) {
                notFoundException.printStackTrace();
            } catch (InvalidCredentialsException invalidCredentialsException) {
                invalidCredentialsException.printStackTrace();
            }
        });
        txtNaam.setClearButtonVisible(true);
        lphLayout.add(lblNaam);
        lphLayout.add(txtNaam);

        grid = new Grid<>();
        grid.setItems(new ArrayList<Persoon>(0));
        grid.addColumn(Persoon::getVoornaam).setHeader("Voornaam").setSortable(true);
        grid.addColumn(Persoon::getNaam).setHeader("Naam").setSortable(true);
        grid.addColumn(Persoon::getGeslacht).setHeader("Geslacht").setSortable(true);
        grid.addColumn(Persoon::getAdres).setHeader("Adres").setSortable(true);
        grid.addColumn(Persoon::getTelefoon).setHeader("Telefoon").setSortable(true);
        grid.addColumn(Persoon::getGsm).setHeader("Gsm").setSortable(true);
        grid.addColumn(Persoon::getEmail).setHeader("E-mail").setSortable(true);
        grid.addColumn(Persoon::getGeboortedatum).setHeader("Geboortedatum").setSortable(true);
        grid.addColumn(new ComponentRenderer<>(pers -> {
            Button b = new Button(new Icon(VaadinIcon.BULLETS));
            b.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            //b.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("inschrijvingen/" + pers.getId())));
            b.addClickListener(e -> {
                ToewijzingDialog dialog = null;
                try {
                    dialog = new ToewijzingDialog(pers);
                } catch (NotFoundException ex) {
                    ex.printStackTrace();
                } catch (InvalidCredentialsException ex) {
                    Notification.show("Je bent niet ingelogd", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    UI.getCurrent().navigate("login");
                    ex.printStackTrace();
                } catch (ParameterInvalidException ex) {
                    ex.printStackTrace();
                }
                try {
                    dialog = new ToewijzingDialog(pers);
                } catch (NotFoundException ex) {
                    ex.printStackTrace();
                } catch (InvalidCredentialsException ex) {
                    Notification.show("Je bent niet ingelogd", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    UI.getCurrent().navigate("login");
                    ex.printStackTrace();
                } catch (ParameterInvalidException ex) {
                    ex.printStackTrace();
                }
                ToewijzingDialog finalDialog = dialog;
                UI.getCurrent().getPage().retrieveExtendedClientDetails(receiver -> {
                    int browserInnerWindowsWidth = receiver.getWindowInnerWidth();
                    int browserInnerWindowsHeight = receiver.getWindowInnerHeight();

                    finalDialog.setWidth((browserInnerWindowsWidth / 2.0) + "px");
                    finalDialog.setHeight((browserInnerWindowsHeight / 2.0) + "px");
                });
                dialog.open();
            });
            return b;
        })).setHeader("Toewijzing");
        grid.setHeightFull();

        //when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        lpvLayout.add(lphLayout);
        lpvLayout.add(grid);
        lpvLayout.setWidth("70%");
        return lpvLayout;
    }

    private Component createEditorLayout() {
        rpvLayout = new VerticalLayout();

        frm = new PersoonFragment();

        rphLayout = new HorizontalLayout();
        rphLayout.setWidthFull();
        rphLayout.setSpacing(true);

        btnCancel = new Button("Annuleren");
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancel.addClickListener(e -> handleClickCancel(e));

        btnCreate = new Button("Toevoegen");
        btnCreate.addClickListener(e -> handleClickCreate(e));

        btnUpdate = new Button("Opslaan");
        btnUpdate.addClickListener(e -> handleClickUpdate(e));
        btnUpdate.setVisible(false);

        btnDelete = new Button("Verwijderen");
        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnDelete.addClickListener(e -> handleClickDelete(e));
        btnDelete.setVisible(false);

        rphLayout.add(btnCancel, btnCreate, btnUpdate, btnDelete);

        rpvLayout.add(frm);
        rpvLayout.add(rphLayout);
        rpvLayout.setWidth("30%");

        return rpvLayout;
    }

    public void loadData() throws NotFoundException, InvalidCredentialsException {
        if (persoonResource != null) {
            List<Persoon> lst = (List<Persoon>) persoonResource.getPersonen("").getBody();
            grid.setItems(lst);
        } else {
            System.err.println("Autowiring failed");
        }
    }

    private void handleClickSearch(ClickEvent event) throws NotFoundException, InvalidCredentialsException {
        if (txtNaam.getValue().trim().length() == 0) {
            grid.setItems((List<Persoon>) persoonResource.getPersonen("").getBody());
        } else {
            String searchterm = txtNaam.getValue().trim();
            grid.setItems(persoonResource.getPersonenSearch(searchterm, ""));
        }
    }

    private void handleClickCancel(ClickEvent event) {
        grid.asSingleSelect().clear();
        frm.resetForm();
        btnCreate.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }

    private void handleClickCreate(ClickEvent event) {
        if (!frm.isformValid()) {
            Notification.show("Er zijn validatiefouten", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            Date d = Date.from(frm.datGeboorte.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            PersoonDTO s = new PersoonDTO( frm.txtVoornaam.getValue(),frm.txtNaam.getValue(),frm.txtWachtwoord.getValue(), java.sql.Date.valueOf(frm.datGeboorte.getValue()) ,frm.txtGeslacht.getValue(),frm.txtAdres.getValue(),frm.txtTelefoon.getValue(),frm.txtGsm.getValue(),frm.txtEmail.getValue());
            ResponseEntity i = persoonResource.postPersoon(s, "");

            Notification.show("Persoon created (id: " + i + ")", 3000, Notification.Position.TOP_CENTER);
            frm.resetForm();
            handleClickSearch(null);
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (ParameterInvalidException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (InvalidCredentialsException e) {
            Notification.show("Je bent niet ingelogd", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
            UI.getCurrent().navigate("login");
            e.printStackTrace();
        }
    }

    private void handleClickUpdate(ClickEvent event) {
        if (!frm.isformValid()) {
            Notification.show("Er zijn validatiefouten", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            Date d = Date.from(frm.datGeboorte.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            PersoonDTO pdto = new PersoonDTO(frm.txtVoornaam.getValue(),frm.txtNaam.getValue(),frm.txtWachtwoord.getValue(), java.sql.Date.valueOf(frm.datGeboorte.getValue()) ,frm.txtGeslacht.getValue(),frm.txtAdres.getValue(),frm.txtTelefoon.getValue(),frm.txtGsm.getValue(),frm.txtEmail.getValue());
            Optional<Persoon> p = persoonRepository.findPersoonByEmailIgnoreCase(frm.txtEmail.getValue());
            persoonResource.putPersoon(p.get().getId(), pdto, "");

            Notification.show("Persoon aangepast", 3000, Notification.Position.TOP_CENTER);
            frm.resetForm();
            handleClickSearch(null);
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (ParameterInvalidException e) {
            e.printStackTrace();
        } catch (InvalidCredentialsException e) {
            Notification.show("Je bent niet ingelogd", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
            UI.getCurrent().navigate("login");
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleClickDelete(ClickEvent event) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Bevestig Persoon verwijderen", event2 -> {
            try {
                Optional<Persoon> p = persoonRepository.findPersoonByEmailIgnoreCase(frm.txtEmail.getValue());
                persoonResource.deletePersoon(p.get().getId(), "");
                Notification.show("Persoon verwijderd", 3000, Notification.Position.TOP_CENTER);
            } catch (IllegalArgumentException e) {
                Notification.show("Het is NIET mogelijk de persoon te verwijderen wegens geregistreerde toewijzigingen.", 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ParameterInvalidException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (InvalidCredentialsException e) {
                Notification.show("Je bent niet ingelogd", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                UI.getCurrent().navigate("login");
                e.printStackTrace();
            }
            frm.resetForm();
            try {
                handleClickSearch(null);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (InvalidCredentialsException e) {
                e.printStackTrace();
            }
            btnCreate.setVisible(true);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);

            dialog.close();
        });

        Button cancelButton = new Button("Cancel", event2 -> {
            dialog.close();
        });

        dialog.add(confirmButton, new Html("<span>&nbsp;</span>"), cancelButton);

        dialog.open();
    }

    private void populateForm(Persoon p) {
        btnCreate.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        if (p != null) {
            // Copy the ID in a hidden field
            if (p.getId() != null) {
                frm.lblID.setText(p.getId().toString());
            } else {
                frm.lblID.setText("");
            }

            if (p.getVoornaam() != null) {
                frm.txtVoornaam.setValue(p.getVoornaam());
            } else {
                frm.txtVoornaam.setValue("");
            }
            if (p.getNaam() != null) {
                frm.txtNaam.setValue(p.getNaam());
            } else {
                frm.txtNaam.setValue("");
            }
            if (p.getWachtwoord() != null) {
                frm.txtWachtwoord.setValue(p.getWachtwoord());
            } else {
                frm.txtWachtwoord.setValue("");
            }
            if (p.getGeslacht() != null) {
                frm.txtGeslacht.setValue(p.getGeslacht());
            } else {
                frm.txtGeslacht.setValue("");
            }
            if (p.getAdres() != null) {
                frm.txtAdres.setValue(p.getAdres());
            } else {
                frm.txtAdres.setValue("");
            }
            if (p.getTelefoon() != null) {
                frm.txtTelefoon.setValue(p.getTelefoon());
            } else {
                frm.txtTelefoon.setValue("");
            }
            if (p.getGsm() != null) {
                frm.txtGsm.setValue(p.getGsm());
            } else {
                frm.txtGsm.setValue("");
            }
            if (p.getEmail() != null) {
                frm.txtEmail.setValue(p.getEmail());
            } else {
                frm.txtEmail.setValue("");
            }

            if (p.getGeboortedatum() != null) {
                try {
                    frm.datGeboorte.setValue(p.getGeboortedatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } catch (NullPointerException e) {
                    frm.datGeboorte.setValue(null);
                }
            } else {
                frm.datGeboorte.setValue(null);
            }
        }
    }

}


