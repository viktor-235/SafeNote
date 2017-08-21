package com.viktor235.safenote;

import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.delegator.Thingable;
import com.viktor235.safenote.notesview.*;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends Stage implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private ListView<Note> listView;
    @FXML
    private Pane notePane;
    @FXML
    private TextField noteTitle;

    private NotesHandler notesHandler;
    private CompositeNote currentParentNote;
    private Note currentNote;
    private INotePane currentNotePane;

    public MainWindowController(NotesHandler notesHandler) {
        this.notesHandler = notesHandler;

        this.getIcons().add(new Image("images/ic_assignment_black_48dp_1x.png"));
        this.getIcons().add(new Image("images/ic_assignment_black_48dp_2x.png"));
        this.getIcons().add(new Image("images/ic_assignment_black_48dp_4x.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        fxmlLoader.setController(this);
        setTitle("Safe Note");
        setWidth(600);
        setHeight(400);
        setMinWidth(400);
        setMinHeight(300);
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
            currentParentNote = compositeNote;
        } else
            return;
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.setAll(compositeNote.getChilds());
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {
            @Override
            public ListCell<Note> call(ListView<Note> listView) {
                ListViewCell cell = new ListViewCell(listView.widthProperty().add(-20));
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
            public void changed(ObservableValue<? extends Note> observable, Note oldValue, Note newValue) {
                currentNote = newValue;
                notePane.getChildren().clear();
                if (newValue == null)
                    return;
                if (newValue.isEncrypted()) {
                    Thingable thing = new Thingable() {
                        @Override
                        public void thing() {

                        }
                    };

                    currentNotePane = new PasswordPane(newValue, thing);
                    notePane.getChildren().add(currentNotePane.getPane());
                    return;
                }

                if (newValue instanceof DefaultNote) {
                    currentNotePane = new DefaultNotePane((DefaultNote) newValue);
                    notePane.getChildren().add(currentNotePane.getPane());
                } else if (newValue instanceof CompositeNote) {
                    currentNotePane = new CompositeNotePane((CompositeNote) newValue);
                    notePane.getChildren().add(currentNotePane.getPane());
                } else {
                    currentNotePane = new EmptyNotePane();
                    notePane.getChildren().add(currentNotePane.getPane());
                }
                if (currentNotePane.getNote() != null)
                    noteTitle.setText(currentNotePane.getNote().getName());
                else
                    noteTitle.setText("");
            }
        });
    }

    public void handleBack(ActionEvent actionEvent) {
        setListView(currentParentNote.getParent());
    }

    public void handleAddNote(ActionEvent actionEvent) {
        currentParentNote.add(new DefaultNote(""));
        notesHandler.saveNotes();
        setListView(currentParentNote);
    }

    public void handleAddCompositeNote(ActionEvent actionEvent) {
        currentParentNote.add(new CompositeNote(""));
        notesHandler.saveNotes();
        setListView(currentParentNote);
    }

    public void handleSave(ActionEvent actionEvent) {
        if (currentNotePane != null) {
            currentNotePane.updateNote(noteTitle.getText());
            notesHandler.saveNotes();
            setListView(currentParentNote);
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        notesHandler.deleteNote(currentNote);
        notesHandler.saveNotes();
        setListView(currentParentNote);
    }
}
