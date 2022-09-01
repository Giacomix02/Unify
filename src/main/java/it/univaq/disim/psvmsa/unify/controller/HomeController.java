package it.univaq.disim.psvmsa.unify.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements DataInitializable, Initializable, Searchable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void search(String text) {
       System.out.println(text);
    }

}
