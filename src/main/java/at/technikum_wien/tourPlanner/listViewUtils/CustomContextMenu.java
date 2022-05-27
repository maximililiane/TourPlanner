package at.technikum_wien.tourPlanner.listViewUtils;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class CustomContextMenu {
    ContextMenu cm;
    MenuItem showLogDetailsItem;

    public CustomContextMenu() {
        cm= new ContextMenu();
        showLogDetailsItem = new MenuItem("show Log details");
        cm.getItems().addAll(showLogDetailsItem);
    }

    public ContextMenu getCm() {
        return cm;
    }

    public void setCm(ContextMenu cm) {
        this.cm = cm;
    }

    public MenuItem getShowLogDetailsItem() {
        return showLogDetailsItem;
    }

    public void setShowLogDetailsItem(MenuItem showLogDetailsItem) {
        this.showLogDetailsItem = showLogDetailsItem;
    }
}
