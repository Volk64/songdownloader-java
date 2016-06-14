/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songdownloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Controller for handling the actions from the user interface.
 * @author Volk
 */
public class SongDownloaderGUIController implements Initializable {
    
    @FXML
    private TextArea linksToFetchTA;
    
    @FXML
    private Label downloadStatusL;
    
    @FXML
    private Label directoryToSave;
    
    //Used for the process creation and directory selection on which the process will be called
    private Process callToYoutubeDL;
    private String currentLink;
    private StringTokenizer linksParsed;
    private DirectoryChooser directoryChooser;
    private File directorySelected;
    
    // Used for retrieving the current window.
    private Node source;
    private Stage stage;
    
    @FXML
    private void handleDownloadButton(ActionEvent event) throws IOException, InterruptedException{
        
        // Check if the user has selected a directory. 
        // If they have not, warn them and stop the method execution.
        if(directorySelected == null){
            downloadStatusL.setText("YOU HAVE NOT SELECTED A FOLDER TO SAVE THE SONGS!");
            return;
        }
        
        linksParsed = new StringTokenizer(linksToFetchTA.getText(), "\n");
        while(linksParsed.hasMoreTokens()){
            currentLink = linksParsed.nextToken();
            callToYoutubeDL = new ProcessBuilder("youtube-dl", "-x", "-o", "\"%(title)s.%(ext)s\"", "--audio-format", "mp3", currentLink).directory(directorySelected).start();
            downloadStatusL.setText("Downloading " + currentLink);
            // Wait until the song has been succesfully downloaded and transformed
            callToYoutubeDL.waitFor();
        }
        
        downloadStatusL.setText("Download status: DONE");
        
    }
    
    @FXML
    private void handleSelectDirectoryButton(ActionEvent event){
        
        //We recover the stage so that we can open a new dialog.
        source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        // We open a dialog, and store the selected directory, 
        // as well as allowing the user to see which directory has been selected.
        directoryChooser = new DirectoryChooser();
        directorySelected = directoryChooser.showDialog(stage);
        directoryToSave.setText(directorySelected.getAbsolutePath());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
