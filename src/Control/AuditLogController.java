package Control;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Oskar on 2015-10-14.
 */
public class AuditLogController {

    @FXML
    private TextArea auditTextArea;

    public void setText(String s){
        auditTextArea.setText(s);
    }
}
