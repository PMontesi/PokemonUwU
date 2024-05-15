package es.cesur.progprojectpok.clases;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class PaneData {
    private ImageView image;
    private Label labelNom;
    private Label labelLvl;
    private Label labelVit;
    private ProgressBar progressBar;

    public PaneData(Pane pane) {

        this.image = (ImageView) pane.lookup("#image");
        this.labelNom = (Label) pane.lookup("#labelNom");
        this.labelLvl = (Label) pane.lookup("#labelLvl");
        this.labelVit = (Label) pane.lookup("#labelVit");
        this.progressBar = (ProgressBar) pane.lookup("#progressBar");

    }

    public void actualizarElementos(String nombre, Image imagen, int nivel, int vitalidad, int vitMax, Pokemon pokemon) {

        image.setImage(imagen);
        labelNom.setText(nombre);
        labelLvl.setText("Nivel: " + nivel);
        labelVit.setText(vitalidad + "/" + vitMax);
        progressBar.setProgress(((double) pokemon.getVitalidad() / pokemon.getVitMax()));


    }

    public ImageView getImage() {
        return image;
    }

    public Label getLabelNom() {
        return labelNom;
    }

    public Label getLabelLvl() {
        return labelLvl;
    }

    public Label getLabelVit() {
        return labelVit;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }


}


