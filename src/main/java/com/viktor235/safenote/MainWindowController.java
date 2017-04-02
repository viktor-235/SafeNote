package com.viktor235.safenote;

import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.notesview.DefaultNotePane;
import com.viktor235.safenote.notesview.ListViewCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends Stage implements Initializable {
    @FXML
    private ListView<Note> listView;
    @FXML
    private Pane notePane;

    private NotesHandler notesHandler;
    private CompositeNote currentCompositeNote;

    public MainWindowController(NotesHandler notesHandler) {
        this.notesHandler = notesHandler;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        fxmlLoader.setController(this);
        setTitle("Safe Note");
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView(notesHandler.getNotes());
    }

    public void setListView(Note note) {
        CompositeNote compositeNote;
        if (note != null && note instanceof CompositeNote) {
            compositeNote = (CompositeNote) note;
            currentCompositeNote = compositeNote;
        } else
            return;
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(compositeNote.getChilds());
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {
            @Override
            public ListCell<Note> call(ListView<Note> listView) {
                ListViewCell cell = new ListViewCell();
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (cell.isEmpty())
                            event.consume();
                        Note selectedNote = listView.getSelectionModel().getSelectedItem();
                        if (event.getClickCount() == 2 && selectedNote instanceof CompositeNote)
                            setListView(selectedNote);
                    }
                });
                return cell;
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {
            public void changed(ObservableValue<? extends Note> observable,
                                Note oldValue, Note newValue) {
                if (newValue instanceof DefaultNote) {
                    DefaultNotePane defaultNotePane = new DefaultNotePane();
                    defaultNotePane.setContent(((DefaultNote) newValue).getText());
                    notePane.getChildren().add(defaultNotePane.getPane());
                } else
                    notePane.getChildren().clear();
            }
        });
    }

    public void clickBack(ActionEvent actionEvent) {
        setListView(currentCompositeNote.getParent());
    }

    public void clickSave(ActionEvent actionEvent) {

    }
}
