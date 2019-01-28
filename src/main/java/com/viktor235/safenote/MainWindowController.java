package com.viktor235.safenote;

import com.viktor235.safenote.composite.CompositeNote;
import com.viktor235.safenote.composite.DefaultNote;
import com.viktor235.safenote.composite.Note;
import com.viktor235.safenote.delegator.PasswordChecker;
import com.viktor235.safenote.delegator.Thingable;
import com.viktor235.safenote.notesview.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends Stage implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private TreeView<Note> notesView;
    @FXML
    private Pane notePane;
    @FXML
    private TextField noteTitle;

    private NotesHandler notesHandler;
    private Note currentNote;
    private INotePane currentNotePane;

    private Thingable saveDelegate;

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

        final BooleanProperty titleChanged = new SimpleBooleanProperty(false); // Variable to store the title field changed state

        noteTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentNotePane != null && !newValue.equals(currentNotePane.getNote().getName())) {
                currentNotePane.getNote().setName(newValue);
                titleChanged.setValue(true);
            }
        });

        noteTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && titleChanged.get()) {
                handleSave(null);
                titleChanged.setValue(false);
            }
        });

        saveDelegate = () -> handleSave(null);

        notesView.setCellFactory(p -> new TreeCellImpl());

//        ObservableList observableList = FXCollections.observableArrayList();
//        observableList.setAll(compositeNote.getChilds());
//        notesView.setItems(observableList);
//        notesView.setCellFactory(new Callback<ListView<Note>, ListCell<Note>>() {
//            @Override
//            public ListCell<Note> call(ListView<Note> listView) {
//                ListViewCell cell = new ListViewCell(listView.widthProperty().add(-20));
//                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        if (cell.isEmpty())
//                            event.consume();
//                        Note selectedNote = listView.getSelectionModel().getSelectedItem();
//                        if (event.getClickCount() == 2 && selectedNote instanceof CompositeNote)
//                            setListView(selectedNote);
//                    }
//                });
//                return cell;
//            }
//        });

        notesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Note>>() {
            public void changed(ObservableValue<? extends TreeItem<Note>> observable, TreeItem<Note> oldValue, TreeItem<Note> newValue) {
                notePane.getChildren().clear();
                if (newValue == null || newValue.getValue() == null) {
                    currentNote = null;
                    return;
                } else
                    currentNote = newValue.getValue();
                if (newValue.getValue().isEncrypted()) {
                    PasswordChecker thing = password -> {
                        if (newValue.getValue() instanceof DefaultNote) {
                            DefaultNote decryptedNote = NotesHandler.decryptNote((DefaultNote) newValue.getValue(), password);
                            currentNotePane = new EncriptedDefaultNotePane(decryptedNote, saveDelegate);
                            notePane.getChildren().add(currentNotePane.getPane());
                        }
                    };

                    currentNotePane = new PasswordPane(newValue.getValue(), thing);
                    notePane.getChildren().add(currentNotePane.getPane());
                    return;
                }

                if (newValue.getValue() instanceof DefaultNote) {
                    currentNotePane = new DefaultNotePane((DefaultNote) newValue.getValue(), saveDelegate);
                    notePane.getChildren().add(currentNotePane.getPane());
                } else if (newValue.getValue() instanceof CompositeNote) {
                    currentNotePane = new CompositeNotePane((CompositeNote) newValue.getValue());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }

    protected void fillViewLevel(TreeItem<Note> currentViewLevel, Note currentNoteLevel) {
        if (currentNoteLevel instanceof CompositeNote) {
            CompositeNote compositeNote = (CompositeNote) currentNoteLevel;
            TreeItem<Note> deeperViewLevel = new TreeItem<>(compositeNote);
            for (Note subNote : compositeNote.getChilds())
                fillViewLevel(deeperViewLevel, subNote);
            currentViewLevel.getChildren().add(deeperViewLevel);
        } else
            currentViewLevel.getChildren().add(new TreeItem<>(currentNoteLevel));
    }

    public void setListView() {
        Note rootNote = notesHandler.getNotes();
        TreeItem<Note> rootItem = new TreeItem<>(rootNote);

        notesView.setRoot(rootItem);

        if (rootNote instanceof CompositeNote) {
            CompositeNote compositeNote = (CompositeNote) rootNote;
            for (Note subNote : compositeNote.getChilds())
                fillViewLevel(rootItem, subNote);
        } else {
            //TODO ERROR
        }
    }

    public void handleAddNote(ActionEvent actionEvent) {
        if (currentNote != null) {
            CompositeNote currentParentNote = currentNote.getParent();
            if (currentParentNote != null) {
                currentParentNote.add(new DefaultNote(""));
                notesHandler.saveNotes();
                setListView();
            }
        }
    }

    public void handleAddCompositeNote(ActionEvent actionEvent) {
        if (currentNote != null) {
            CompositeNote currentParentNote = currentNote.getParent();
            if (currentParentNote != null) {
                currentParentNote.add(new CompositeNote(""));
                notesHandler.saveNotes();
                setListView();
            }
        }
    }

    public void handleSave(ActionEvent actionEvent) {
        if (currentNotePane != null) {
            currentNotePane.updateNote(noteTitle.getText());
            notesHandler.saveNotes();
            setListView();
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        notesHandler.deleteNote(currentNote);
        notesHandler.saveNotes();
        setListView();
    }
}
