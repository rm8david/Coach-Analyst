package com.example.tfg.controller;

import com.example.tfg.model.Match;
import com.example.tfg.service.ResultsService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ResultsController {

    private static final Logger logger = LoggerFactory.getLogger(ResultsController.class);

    @Autowired
    private ResultsService resultsService;

    //sobrecarga del método setupResultsView
    public void setupResultsView(Scene scene, int idEquipo){
        VBox resultsContainer = (VBox) scene.lookup("#resultsContainer");
        Label defaultLabel = (Label) scene.lookup("#defaultLabel");

        if (resultsContainer == null) {
            logger.error("No se encontró el contenedor de resultados en la escena");
            return;
        }

        // Eliminar el label de carga si existe
        if (defaultLabel != null) {
            resultsContainer.getChildren().remove(defaultLabel);
        }

        // Cargar y mostrar resultados
        loadResults(resultsContainer, idEquipo);
    }

    public void setupResultsView(Scene scene) {
        VBox resultsContainer = (VBox) scene.lookup("#resultsContainer");
        Label defaultLabel = (Label) scene.lookup("#defaultLabel");

        if (resultsContainer == null) {
            logger.error("No se encontró el contenedor de resultados en la escena");
            return;
        }

        // Eliminar el label de carga si existe
        if (defaultLabel != null) {
            resultsContainer.getChildren().remove(defaultLabel);
        }

        // Cargar y mostrar resultados
        loadResults(resultsContainer);
    }

    private void loadResults(VBox container, int idEquipo) {
        container.getChildren().clear();

        try {
            List<Match> matches = resultsService.findMatchesByTeamsInSameTournaments(idEquipo);


            if (matches == null || matches.isEmpty()) {
                Label noResultsLabel = new Label("No hay partidos disponibles");
                noResultsLabel.setStyle("-fx-text-fill: #2C3E50; -fx-font-size: 20px;");
                container.getChildren().add(noResultsLabel);
                return;
            }

            // Agregar cada partido al contenedor
            for (Match match : matches) {
                VBox matchPanel = createMatchPanel(match);
                container.getChildren().add(matchPanel);
            }

        } catch (Exception e) {
            logger.error("Error al cargar los resultados", e);
            Label errorLabel = new Label("Error al cargar los partidos");
            errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 20px;");
            container.getChildren().add(errorLabel);
        }
    }

    //sobrecarga del metodo
    private void loadResults(VBox container) {
        container.getChildren().clear();

        try {
            // Mostrar todos los partidos cuando no hay equipo específico
            List<Match> matches = resultsService.findAll();

            if (matches == null || matches.isEmpty()) {
                Label noResultsLabel = new Label("No hay partidos disponibles");
                noResultsLabel.setStyle("-fx-text-fill: #2C3E50; -fx-font-size: 20px;");
                container.getChildren().add(noResultsLabel);
                return;
            }

            // Agregar cada partido al contenedor
            for (Match match : matches) {
                VBox matchPanel = createMatchPanel(match);
                container.getChildren().add(matchPanel);
            }

        } catch (Exception e) {
            logger.error("Error al cargar los resultados", e);
            Label errorLabel = new Label("Error al cargar los partidos");
            errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 20px;");
            container.getChildren().add(errorLabel);
        }
    }
    private VBox createMatchPanel(Match match) {
        VBox matchPanel = new VBox();
        matchPanel.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);");
        matchPanel.setPadding(new Insets(20));
        matchPanel.setSpacing(15);
        matchPanel.setMaxWidth(1000);

        // Fecha y hora del partido
        LocalDateTime dateTime = match.getDateTime().toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Text dateTimeText = new Text(dateTime.format(formatter));
        dateTimeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        dateTimeText.setFill(Color.valueOf("#2C3E50"));

        HBox dateTimeBox = new HBox(dateTimeText);
        dateTimeBox.setAlignment(Pos.CENTER);
        matchPanel.getChildren().add(dateTimeBox);

        // Panel para el resultado con equipos y marcador
        HBox resultPanel = new HBox();
        resultPanel.setAlignment(Pos.CENTER);
        resultPanel.setSpacing(15);

        // Nombre del equipo local
        Text homeTeamText = new Text(match.getHomeTeam().getName());
        homeTeamText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        homeTeamText.setFill(Color.valueOf("#2C3E50"));

        HBox homeTeamBox = new HBox(homeTeamText);
        homeTeamBox.setAlignment(Pos.CENTER_RIGHT);
        homeTeamBox.setPrefWidth(400);
        HBox.setHgrow(homeTeamBox, Priority.ALWAYS);

        // Resultado del partido
        Text scoreText = new Text(match.getHomeScore() + " - " + match.getAwayScore());
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        scoreText.setFill(Color.valueOf("#2C3E50"));

        HBox scoreBox = new HBox(scoreText);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setPrefWidth(100);

        // Nombre del equipo visitante
        Text awayTeamText = new Text(match.getAwayTeam().getName());
        awayTeamText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        awayTeamText.setFill(Color.valueOf("#2C3E50"));

        HBox awayTeamBox = new HBox(awayTeamText);
        awayTeamBox.setAlignment(Pos.CENTER_LEFT);
        awayTeamBox.setPrefWidth(400);
        HBox.setHgrow(awayTeamBox, Priority.ALWAYS);

        // Añadir componentes al panel de resultados
        resultPanel.getChildren().addAll(homeTeamBox, scoreBox, awayTeamBox);
        matchPanel.getChildren().add(resultPanel);


        return matchPanel;
    }
}