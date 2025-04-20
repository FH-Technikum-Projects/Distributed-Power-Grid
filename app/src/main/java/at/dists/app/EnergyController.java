package at.dists.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.http.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

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

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        startTimeComboBox.getItems().addAll("00:00", "01:00", "...", "23:00");
        endTimeComboBox.getItems().addAll("00:00", "01:00", "...", "23:00");
        startTimeComboBox.getSelectionModel().selectFirst();
        endTimeComboBox.getSelectionModel().selectFirst();

        handleRefresh(); // Aktuelle Daten laden
    }

    @FXML
    private void handleRefresh() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject json = new JSONObject(response);
                    communityPoolLabel.setText(json.getDouble("communityPool") + "%");
                    gridPortionLabel.setText(json.getDouble("gridPortion") + "%");
                });
    }

    @FXML
    private void handleShowHistoricalData() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String startTime = startTimeComboBox.getValue();
        String endTime = endTimeComboBox.getValue();

        String startDateTime = startDate.format(DateTimeFormatter.ISO_DATE) + "T" + startTime + ":00";
        String endDateTime = endDate.format(DateTimeFormatter.ISO_DATE) + "T" + endTime + ":00";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/historical?start=" + startDateTime + "&end=" + endDateTime))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject json = new JSONObject(response);
                    communityProducedLabel.setText(json.getDouble("communityProduced") + " kWh");
                    communityUsedLabel.setText(json.getDouble("communityUsed") + " kWh");
                    gridUsedLabel.setText(json.getDouble("gridUsed") + " kWh");
                });
    }
}
