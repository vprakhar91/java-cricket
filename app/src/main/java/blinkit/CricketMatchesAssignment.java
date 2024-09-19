import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class CricketMatchesAssignment {

    public static void main(String[] args) {
        try {
           
            String apiUrl = "https://api.cuvora.com/car/partner/cricket-data";
            String apiKey = "test-creds@2320";
            String jsonData = getApiResponse(apiUrl, apiKey);

           
            JSONArray matches = new JSONArray(jsonData);

           
            String highestScoreResult = getHighestScore(matches);


            int matchesWith300PlusScore = getMatchesWith300PlusScore(matches);

           
            System.out.println(highestScoreResult);
            System.out.println("Number Of Matches with total 300 Plus Score : " + matchesWith300PlusScore);

            System.out.println("Tasks completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getApiResponse(String apiUrl, String apiKey) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apiKey", apiKey);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return response.toString();
    }

    private static String getHighestScore(JSONArray matches) {
        int highestScore = 0;
        String teamName = "";

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            int team1Score = match.getInt("t1s");
            int team2Score = match.getInt("t2s");

            if (team1Score > highestScore) {
                highestScore = team1Score;
                teamName = match.getString("t1");
            }

            if (team2Score > highestScore) {
                highestScore = team2Score;
                teamName = match.getString("t2");
            }
        }

        return "Highest Score : " + highestScore + " and Team Name is : " + teamName;
    }

    private static int getMatchesWith300PlusScore(JSONArray matches) {
        int matchesCount = 0;

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            int team1Score = match.getInt("t1s");
            int team2Score = match.getInt("t2s");

            if (team1Score + team2Score > 300) {
                matchesCount++;
            }
        }

        return matchesCount;
    }
}