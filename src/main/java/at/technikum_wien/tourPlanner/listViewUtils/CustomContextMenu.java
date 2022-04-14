package at.technikum_wien.tourPlanner.listViewUtils;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class CustomContextMenu {
    ContextMenu cm;
    MenuItem detailsMenuItem, logsMenuItem, editMenuItem;

    public CustomContextMenu() {
        cm= new ContextMenu();
        detailsMenuItem = new MenuItem("details");
        logsMenuItem = new MenuItem("logs");
        editMenuItem = new MenuItem("edit");
        cm.getItems().addAll(detailsMenuItem, logsMenuItem, editMenuItem);
    }

    public ContextMenu getCm() {
        return cm;
    }

    public void setCm(ContextMenu cm) {
        this.cm = cm;
    }

    public MenuItem getDetailsMenuItem() {
        return detailsMenuItem;
    }

    public void setDetailsMenuItem(MenuItem detailsMenuItem) {
        this.detailsMenuItem = detailsMenuItem;
    }

    public MenuItem getLogsMenuItem() {
        return logsMenuItem;
    }

    public void setLogsMenuItem(MenuItem logsMenuItem) {
        this.logsMenuItem = logsMenuItem;
    }

    public MenuItem getEditMenuItem() {
        return editMenuItem;
    }

    public void setEditMenuItem(MenuItem editMenuItem) {
        this.editMenuItem = editMenuItem;
    }
}
