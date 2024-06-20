import java.util.HashMap;
import java.util.Map;

/**
 * La classe FlightTimeCalculator fornisce metodi per calcolare la distanza e il tempo di volo medio tra due aeroporti.
 */
public class FlightTimeCalculator {

    // Raggio della Terra in km
    private static final double EARTH_RADIUS_KM = 6371.0;
    // Velocità media di volo in km/h
    private static final double AVERAGE_FLIGHT_SPEED_KMH = 850.0;

    // Mappa delle coordinate degli aeroporti
    private static final Map<String, double[]> airportCoordinates = new HashMap<>();

    // Inizializzazione statica delle coordinate degli aeroporti
    static {
        airportCoordinates.put("JFK", new double[]{40.6413, -73.7781});
        airportCoordinates.put("LHR", new double[]{51.4700, -0.4543});
        airportCoordinates.put("CDG", new double[]{49.0097, 2.5479});
        airportCoordinates.put("FRA", new double[]{50.0379, 8.5622});
        airportCoordinates.put("DXB", new double[]{25.2532, 55.3657});
        airportCoordinates.put("HND", new double[]{35.5494, 139.7798});
        airportCoordinates.put("LAX", new double[]{33.9416, -118.4085});
        airportCoordinates.put("SIN", new double[]{1.3644, 103.9915});
        airportCoordinates.put("PEK", new double[]{40.0799, 116.6031});
        airportCoordinates.put("AMS", new double[]{52.3105, 4.7683});
        airportCoordinates.put("SYD", new double[]{-33.9399, 151.1753});
        airportCoordinates.put("YYZ", new double[]{43.6777, -79.6248});
        airportCoordinates.put("MUC", new double[]{48.3538, 11.7861});
        airportCoordinates.put("ICN", new double[]{37.4602, 126.4407});
        airportCoordinates.put("IST", new double[]{41.2753, 28.7519});
        airportCoordinates.put("DEN", new double[]{39.8561, -104.6737});
        airportCoordinates.put("MAD", new double[]{40.4983, -3.5676});
        airportCoordinates.put("SFO", new double[]{37.7749, -122.4194});
        airportCoordinates.put("HKG", new double[]{22.3080, 113.9185});
        airportCoordinates.put("ORD", new double[]{41.9742, -87.9073});
    }

    // Metodo per calcolare la distanza tra due aeroporti usando la formula dell'haversine
    /**
     * Calcola la distanza in km tra due coordinate geografiche utilizzando la formula dell'haversine.
     *
     * @param coord1 Le coordinate geografiche del primo aeroporto.
     * @param coord2 Le coordinate geografiche del secondo aeroporto.
     * @return La distanza in km tra i due aeroporti.
     */
    public static double calculateDistance(double[] coord1, double[] coord2) {
        double lat1 = Math.toRadians(coord1[0]);
        double lon1 = Math.toRadians(coord1[1]);
        double lat2 = Math.toRadians(coord2[0]);
        double lon2 = Math.toRadians(coord2[1]);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    // Metodo per calcolare il tempo di volo medio tra due aeroporti
    /**
     * Calcola il tempo di volo medio in formato DateTime tra due aeroporti utilizzando la velocità di volo media.
     *
     * @param airportCode1 Il codice dell'aeroporto di partenza.
     * @param airportCode2 Il codice dell'aeroporto di arrivo.
     * @return Un oggetto DateTime che rappresenta la durata del volo medio.
     * @throws IllegalArgumentException se viene fornito un codice aeroportuale non valido.
     */
    public static DateTime calculateFlightTime(String airportCode1, String airportCode2) {
        double[] coord1 = airportCoordinates.get(airportCode1);
        double[] coord2 = airportCoordinates.get(airportCode2);

        if (coord1 == null || coord2 == null) {
            throw new IllegalArgumentException("Invalid airport code provided.");
        }

        double distance = calculateDistance(coord1, coord2);
        double flightHours = distance / AVERAGE_FLIGHT_SPEED_KMH;

        long flightMilliseconds = (long) (flightHours * 60 * 60 * 1000);

        // Restituisce un oggetto DateTime che rappresenta la durata del volo.
        return new DateTime(flightMilliseconds);
    }

    // Metodo per eseguire un test di calcolo del tempo di volo medio
    public static void main(String[] args) {
        String airport1 = "JFK";
        String airport2 = "LHR";

        DateTime flightTime = calculateFlightTime(airport1, airport2);
        System.out.printf("The average flight time between %s and %s is %s hours.%n", airport1, airport2, flightTime.format("HH:mm:ss"));

        // Example of adding two flight times
        DateTime totalFlightTime = calculateFlightTime(airport1, airport2);
        totalFlightTime.add(flightTime);
        System.out.printf("Total flight time for two flights between %s and %s is %s hours.%n", airport1, airport2, totalFlightTime.format("HH:mm:ss"));
        DateTime x = new DateTime();
        System.out.println(x);
        x.add(flightTime);
        System.out.println(x);

    }
}
