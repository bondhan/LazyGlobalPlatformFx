/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyglobalplatformfx;

import apdu4j.APDUReplayProvider;
import apdu4j.TerminalManager;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author bondhan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button refreshBtn;

    @FXML
    private ComboBox terminalsListCmbBox;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private void refreshTerminalList(ActionEvent event) {

        final TerminalFactory tf;

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
        System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
        System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");

        try {
            tf = TerminalManager.getTerminalFactory(null);

            CardTerminals terminals = tf.terminals();

            terminalsListCmbBox.setValue(null);
            
            for (CardTerminal term : terminals.list()) {
                System.out.println((term.isCardPresent() ? "[*] " : "[ ] ") + term.getName());
        
                terminalsListCmbBox.getItems().add(term.getName());
            }

        } catch (CardException e) {
            // Sensible wrapper for the different PC/SC exceptions
            if (TerminalManager.getExceptionMessage(e) != null) {
                System.out.println("PC/SC failure: " + TerminalManager.getExceptionMessage(e));
            } else {
                e.printStackTrace(); // TODO: remove
                fail("CardException, terminating");
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void showMsg(String msg) {
        outputTextArea.appendText(msg);
    }

    private static void fail(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

}
