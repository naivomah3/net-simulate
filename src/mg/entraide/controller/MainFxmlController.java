/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javax.xml.bind.JAXBException;
import mg.entraide.object.LineObject;
import mg.entraide.object.InterfaceObject;
import mg.entraide.object.HostObject;
import mg.entraide.object.HostObjectWrapper;
import mg.entraide.object.HostObjectWrappers;
import mg.entraide.object.InterfaceObjects;
import mg.entraide.object.LineObjectWrapper;
import mg.entraide.tools.XmlMarshallerTools;
import mg.entraide.view.MainFxmlView;

/**
 * FXML Controller class
 *
 * @author RANAIVOSON
 */
public class MainFxmlController implements Initializable {

    
    /**
     * non-FXML variables
     */
    private int routerCompteur = 0;
    private int hostCompteur = 0;
    private HostObject hostObj = null;
    private String hostName;
    private boolean hostClicked = false;
    private boolean routerClicked = false;
    private boolean wireClicked = false;
    private int compteRouter = 0;
    private int compteInterfaceSelected = 0;
    public static List<InterfaceObject> listInterfaceObjectMainPanel = new ArrayList<>(); /* pour que la liste des interfaces soit consultable par tous les routers et les hosts */
    public static List<LineObject> listLineObjectsMainPanel = new ArrayList<>();
    private List<HostObject> listHostObjectMainPanel  = new ArrayList<>();
    
    /**
     * FXML variables
     */
    @FXML
    ImageView imageViewHost;
    @FXML
    ImageView imageViewRouter;
    @FXML
    ImageView imageViewWired;
    @FXML
    ImageView imageViewClear;
    @FXML
    private Pane paneScreen;
    @FXML
    GridPane gridPaneScreen;
    @FXML
    ScrollPane scrollPaneScreen;
    @FXML
    Menu fileMenu;

    public MainFxmlController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridPaneScreen.getStyleClass().add("content");
        paneScreen.setCursor(Cursor.HAND);
        imageViewHost.setCursor(Cursor.HAND);
        imageViewRouter.setCursor(Cursor.HAND);
        imageViewWired.setCursor(Cursor.HAND);
        imageViewClear.setCursor(Cursor.HAND);
//        MainFxmlView.stagePublic.setOnCloseRequest(onClosedClickEvent);
        
    }
    
    /************
     *All EVENTS* 
     * @param t
     ************/
    @FXML
    public void imageViewHostOnClickedEvent(MouseEvent t) {
        paneScreen.setCursor(Cursor.HAND);
        if (t.getButton() == MouseButton.PRIMARY) {
            if (!isHostClicked()) {
                setHostClicked(true);
                setRouterClicked(false);
                setWireClicked(false);
            } else {
                setHostClicked(false);
            }
        }
    }
    @FXML
    public void imageViewRouterOnClickedEvent(MouseEvent t) {
        paneScreen.setCursor(Cursor.HAND);
        if (t.getButton() == MouseButton.PRIMARY) {
            if (!isRouterClicked()) {
                setRouterClicked(true);
                setHostClicked(false);
                setWireClicked(false);
            } else {
                setRouterClicked(false);
            }
        }
    }
    @FXML
    public void imageViewWiredOnClickedEvent(MouseEvent t) {
        paneScreen.setCursor(Cursor.HAND);
        if (t.getButton() == MouseButton.PRIMARY) {
            if (!isWireClicked()) {
                setWireClicked(true);
                setHostClicked(false);
                setRouterClicked(false);
            } else {
                setWireClicked(false);
                for (InterfaceObject interfaceIterator : listInterfaceObjectMainPanel) {
                    if (interfaceIterator.getInterfaceStatus() == 1) {
                        interfaceIterator.setInterfaceStatus(0);
                        compteInterfaceSelected = 0;
                    }
                }
            }
        }
    }
    @FXML
    public void imageViewClearOnClickedEvent () {
        listInterfaceObjectMainPanel = new ArrayList<>();
        listLineObjectsMainPanel = new ArrayList<>();
        listHostObjectMainPanel = new ArrayList<>();
        paneScreen.getChildren().clear();
        hostCompteur = 0;
        routerCompteur = 0;
    }
    @FXML
    public void imageViewClearOnPressedEvent () {
        imageViewClear.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/remove-pressed.png")));
    }
    @FXML
    public void imageViewClearOnReleasedEvent () {
        imageViewClear.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/remove.png")));
    }
    @FXML
    public void menuItemExitEvent () throws JAXBException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Would you like to save the current project ? ");

        ButtonType buttonTypeYes = new ButtonType("Yes, save it");
        ButtonType buttonTypeNo = new ButtonType("No, discard");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo,  buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            saveProject(true);
        } else if (result.get() == buttonTypeNo) {
            MainFxmlView.stagePublic.close();
        } else {
            
        }
        
    }
    @FXML 
    public void menuItemOpenEvent() {
        openProject();
    }
    @FXML
    public void menuItemSaveEvent() throws JAXBException, IOException {
        saveProject(false);
    }
    @FXML
    public void paneOnMouseClickedEvent(MouseEvent t) {
        /**
        * add componement to paneScreen
        */
        if (isHostClicked() || isRouterClicked()) {
            if (isHostClicked()) {
                hostName = "Host";
                hostObj = new HostObject(
                        this, 
                        hostName + hostCompteur, 
                        (t.getX()-37.7), 
                        (t.getY()-40.5));
                hostObj.addImageToMainPanel(false);
                listHostObjectMainPanel.add(hostObj);
                System.out.println(" hostCompteur : " + hostCompteur);
                hostCompteur++;
            } else if (isRouterClicked()) {
                hostName = "Router";
                hostObj = new HostObject(
                        this, 
                        hostName + routerCompteur, 
                        (t.getX()-37.7), 
                        (t.getY()-40.5));
                hostObj.addImageToMainPanel(false);
                listHostObjectMainPanel.add(hostObj);
                System.out.println(" routerCompteur : " + routerCompteur);
                routerCompteur++;
            }
            setHostClicked(false);
            setRouterClicked(false);

        }
    }
    /*****************
     * LOCAL METHODD *
     *****************/
    public void updateInterfaceStatusToDisconnected() {
        for (HostObject hostIterator : listHostObjectMainPanel) {
            for (InterfaceObject interfaceIterator : hostIterator.getInterfaceObj()) {
                if (interfaceIterator.getInterfaceStatus() == 1) {
                    interfaceIterator.setInterfaceStatus(0);
                    compteInterfaceSelected = 0;
                    setWireClicked(false);
                }
            }
        }
    }
    private HostObjectWrappers loadInterfaceObjectsFromFile () throws JAXBException {
        return XmlMarshallerTools.unmarshallerXml(new File("config-host.zxml"));
    }
    
    public void saveProject(boolean isStagePublicClose) throws IOException, JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save project");
        fileChooser.setInitialFileName("zns-project.znsp");
        File savedFile = fileChooser.showSaveDialog(MainFxmlView.stagePublic);
        if (savedFile != null) {
            HostObjectWrappers hostObjectWrappers = new HostObjectWrappers();
            for (HostObject hostObjectIterator : listHostObjectMainPanel) {
                HostObjectWrapper hostObjectWrapper = new HostObjectWrapper();
                InterfaceObjects interfaceObjects = new InterfaceObjects();

                for (InterfaceObject interfaceObjectIterator : hostObjectIterator.getInterfaceObj()) {
                    interfaceObjects.getInterfaceObjects().add(interfaceObjectIterator);
//                System.out.println("hostObjectIterator.getInterfaceObj() : " +hostObjectIterator.getInterfaceObj().get(0).getIpAddressObj().getIpAddress());
                }
                hostObjectWrapper.setHostName(hostObjectIterator.getHostName());
                hostObjectWrapper.getInterfaceObjects().add(interfaceObjects);
                hostObjectWrappers.getHostObjectWrappers().add(hostObjectWrapper);
            }

            for (LineObject listLineObjectIterator : listLineObjectsMainPanel) {
                LineObjectWrapper lineObjectWrapper = new LineObjectWrapper();
                lineObjectWrapper.setInterfaceNameBegin(listLineObjectIterator.interfaceNameBegin);
                lineObjectWrapper.setInterfaceNameEnd(listLineObjectIterator.interfaceNameEnd);
                lineObjectWrapper.setHostNameBegin(listLineObjectIterator.hostNameBegin);
                lineObjectWrapper.setHostNameEnd(listLineObjectIterator.hostNameEnd);
                lineObjectWrapper.setStartX(listLineObjectIterator.startX.doubleValue());
                lineObjectWrapper.setStartY(listLineObjectIterator.startY.doubleValue());
                lineObjectWrapper.setEndX(listLineObjectIterator.endX.doubleValue());
                lineObjectWrapper.setEndY(listLineObjectIterator.endY.doubleValue());
                hostObjectWrappers.getLineObjectWrappers().add(lineObjectWrapper);
            }

            XmlMarshallerTools.marshalingXml(hostObjectWrappers, new File(savedFile.toString()));
            System.out.println("File saved: " + savedFile.toString());
            if (isStagePublicClose) {
                MainFxmlView.stagePublic.close();
            }

        } else {
            System.out.println("File cancel ");
        }

    }

    private void openProject() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Zanatany NS Project (*.znsp)", "*.znsp");
        fileChooser.getExtensionFilters().add(extFilter);
        File znspFile = fileChooser.showOpenDialog(MainFxmlView.stagePublic);
        if (znspFile != null) {
            HostObjectWrappers hostObjectWrappers = new HostObjectWrappers();
            HostObject hostObjectP = null;
            try {
                hostObjectWrappers = XmlMarshallerTools.unmarshallerXml(new File(znspFile.toString()));;
                if (!hostObjectWrappers.getHostObjectWrappers().isEmpty()) {
                    for (HostObjectWrapper listHostObjectWrapperIterator : hostObjectWrappers.getHostObjectWrappers()) {
                        InterfaceObject eth0 = null;
                        InterfaceObject eth1 = null;
                        String hostNameP = null;
                        Double positionHostX = null;
                        Double positionHostY = null;
                        for (InterfaceObjects listInterfaceObjectWrappersIterator : listHostObjectWrapperIterator.getInterfaceObjects()) {
                            eth0 = listInterfaceObjectWrappersIterator.getInterfaceObjects().get(0);
                            eth1 = listInterfaceObjectWrappersIterator.getInterfaceObjects().get(1);
                            hostNameP = listInterfaceObjectWrappersIterator.getInterfaceObjects().get(0).getRouteurName();
                            positionHostX = listInterfaceObjectWrappersIterator.getInterfaceObjects().get(0).getPositionHostX();
                            positionHostY = listInterfaceObjectWrappersIterator.getInterfaceObjects().get(0).getPositionHostY();
                        }

                        hostObjectP = new HostObject(this, hostNameP, positionHostX, positionHostY, eth0, eth1);
                        listHostObjectMainPanel.add(hostObjectP);
                        /**
                         * Add Image to mainPanel
                         */
                        hostObjectP.addImageToMainPanel(true);
                        if (hostNameP.contains("Host")) {
                            hostCompteur++;
                        } else if (hostNameP.contains("Router")) {
                            routerCompteur++;
                        }

                    }
                    for (LineObjectWrapper listLineObjectWrappersIterator : hostObjectWrappers.getLineObjectWrappers()) {
                        LineObjectWrapper lineObjectWrapper = listLineObjectWrappersIterator;
                        /**
                         * Add Line to mainPanel
                         */
                        hostObjectP.addLineToMainPanel(
                                lineObjectWrapper.getHostNameBegin(),
                                lineObjectWrapper.getHostNameEnd(),
                                lineObjectWrapper.getInterfaceNameBegin(),
                                lineObjectWrapper.getInterfaceNameEnd(),
                                lineObjectWrapper.getStartX(),
                                lineObjectWrapper.getStartY(),
                                lineObjectWrapper.getEndX(),
                                lineObjectWrapper.getEndY());

                    }
                }
            } catch (JAXBException ex) {
                Logger.getLogger(MainFxmlController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /***********************
     * Getters and Setters *
     ***********************/
    public boolean isWireClicked() {
        return wireClicked;
    }
    public boolean isHostClicked() {
        return hostClicked;
    }
    public boolean isRouterClicked() {
        return routerClicked;
    }
    public int getCompteInterfaceSelected() {
        return compteInterfaceSelected;
    }
    public void setCompteInterfaceSelected(int compteInterfaceSelected) {
        this.compteInterfaceSelected = compteInterfaceSelected;
    }
    public List<HostObject> getListHostObjectMainPanel() {
        return listHostObjectMainPanel;
    }
    public void setListHostObjectMainPanel(List<HostObject> listHostObjectMainPanel) {
        listHostObjectMainPanel = listHostObjectMainPanel;
    } 
    public void setHostClicked(boolean hostClicked) {
        if (hostClicked) {
            imageViewHost.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/host-disabled.png")));
        } else {
            imageViewHost.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/host-enabled.png")));
        }
        this.hostClicked = hostClicked;
    }   
    public void setRouterClicked(boolean routerClicked) {
        if (routerClicked) {
            imageViewRouter.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/router-disabled.png")));
        } else {
            imageViewRouter.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/router-enabled.png")));
        }
        this.routerClicked = routerClicked;
    }
    public void setWireClicked(boolean wireClicked) {
        if (wireClicked) {
            imageViewWired.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/wired-disabled.png")));
        } else {
            imageViewWired.setImage(new Image(MainFxmlController.class.getResourceAsStream("/mg/entraide/images/wired-enabled.png")));
        }
        this.wireClicked = wireClicked;
    }
    public Pane getPaneScreen() {
        return paneScreen;
    }
    public void setPaneScreen(Pane paneScreen) {
        this.paneScreen = paneScreen;
    }
    
    
 
}
