-- Tabelle für die Usage-Daten
CREATE TABLE IF NOT EXISTS usage_data (
                                          id SERIAL PRIMARY KEY,
                                          hour TIMESTAMP NOT NULL,
                                          community_produced NUMERIC,
                                          community_used NUMERIC,
                                          grid_used NUMERIC
);

-- Tabelle für die aktuellen Prozentwerte
CREATE TABLE IF NOT EXISTS current_percentage (
                                                  id SERIAL PRIMARY KEY,
                                                  hour TIMESTAMP NOT NULL,
                                                  community_depleted NUMERIC,
                                                  grid_portion NUMERIC
);

-- Beispiel-Daten
INSERT INTO current_percentage (hour, community_depleted, grid_portion)
VALUES ('2025-01-10T14:00:00', 100.0, 5.63);
