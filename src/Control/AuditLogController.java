package Control;

import Model.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Oskar on 2015-10-14.
 */
public class AuditLogController {

    @FXML
    private TextArea auditTextArea;
    private Board board;

    public void setText(){
        auditTextArea.setText(board.getAuditLog());
    }

    public void setBoard(Board board){this.board = board;}
}
