package com.example.english_flashcards.controller.deck;

import com.example.english_flashcards.entity.Cards;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

public class CheckBoxTableCell<S> extends TableCell<S, Boolean> {

    private final CheckBox checkBox;

    public CheckBoxTableCell() {
        this.checkBox = new CheckBox();
        this.checkBox.setAlignment(javafx.geometry.Pos.CENTER);
        this.checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!isEditing()) {
                    commitEdit(newValue == null ? false : newValue);
                }
            }
        });
        setAlignment(javafx.geometry.Pos.CENTER);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setEditable(true);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            if (getTableRow().getItem() != null) {
                setGraphic(checkBox);
                checkBox.setSelected(item);
            } else {
                setGraphic(null);
            }
        } else {
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.requestFocus();
    }

    @Override
    public void commitEdit(Boolean newValue) {
        super.commitEdit(newValue);
        if (getTableRow().getItem() != null) {
            S rowData = getTableRow().getItem();
            ((Cards) rowData).selectedProperty().set(newValue);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }
}
