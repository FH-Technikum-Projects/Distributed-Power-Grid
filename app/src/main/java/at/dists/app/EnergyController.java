package at.dists.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class EnergyController {

    @FXML private Label communityPoolLabel;
    @FXML private Label gridPortionLabel;
    @FXML private Label communityProducedLabel;
    @FXML private Label communityUsedLabel;
    @FXML private Label gridUsedLabel;

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> startTimeComboBox;
    @FXML private ComboBox<String> endTimeComboBox;

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final DateTimeFormatter dateFmt = DateTimeFormatter.ISO_DATE;

    @FXML
    public void initialize() {
        // Comboboxen mit Stunden füllen (00:00 bis 23:00)
        var hours = IntStream.range(0, 24)
                .mapToObj(i -> String.format("%02d:00", i))
                .toList();
        startTimeComboBox.setItems(FXCollections.observableList(hours));
        endTimeComboBox.setItems(FXCollections.observableList(hours));
        startTimeComboBox.getSelectionModel().select("00:00");
        endTimeComboBox.getSelectionModel().select("23:00");

        // Default‐Datum auf heute setzen
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        // Bei Start direkt aktuelle Werte holen
        fetchCurrent();
    }

    @FXML
    private void handleRefresh() {
        fetchCurrent();
    }

    private void fetchCurrent() {
        var req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .GET()
                .build();

        http.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateCurrentUI)
                .exceptionally(ex -> {
                    showError("Fehler beim Laden aktueller Daten: " + ex.getMessage());
                    return null;
                });
    }

    private void updateCurrentUI(String jsonText) {
        try {
            JsonNode root = mapper.readTree(jsonText);
            double communityPct = root.path("communityDepleted").asDouble();
            double gridPct      = root.path("gridPortion").asDouble();

            String cp = String.format("%.2f%%", communityPct);
            String gp = String.format("%.2f%%", gridPct);

            Platform.runLater(() -> {
                communityPoolLabel.setText(cp);
                gridPortionLabel.setText(gp);
            });
        } catch (Exception e) {
            showError("Parsing‑Fehler current: " + e.getMessage());
        }
    }


    @FXML
    private void handleShowHistoricalData() {
        LocalDate sDate = startDatePicker.getValue();
        LocalDate eDate = endDatePicker.getValue();
        String sTime = startTimeComboBox.getValue();
        String eTime = endTimeComboBox.getValue();

        String start = sDate.format(dateFmt) + "T" + sTime + ":00";
        String end   = eDate.format(dateFmt) + "T" + eTime + ":00";

        var uri = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", start, end);
        var req = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        http.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateHistoricUI)
                .exceptionally(ex -> {
                    showError("Fehler beim Laden historischer Daten: " + ex.getMessage());
                    return null;
                });
    }

    private void updateHistoricUI(String jsonText) {
        try {
            JsonNode root = mapper.readTree(jsonText);
            double prod = root.path("communityProduced").asDouble();
            double used = root.path("communityUsed").asDouble();
            double grid = root.path("gridUsed").asDouble();

            String sProd = String.format("%.3f kWh", prod);
            String sUsed = String.format("%.3f kWh", used);
            String sGrid = String.format("%.3f kWh", grid);

            Platform.runLater(() -> {
                communityProducedLabel.setText(sProd);
                communityUsedLabel.setText(sUsed);
                gridUsedLabel.setText(sGrid);
            });
        } catch (Exception e) {
            showError("Parsing‑Fehler historical: " + e.getMessage());
        }
    }


    private void showError(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            alert.showAndWait();
        });
    }
}
