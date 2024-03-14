import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Weatherforecast {
	private static JFrame frame;
	private static JTextField locationfield;
	private static JTextArea weatherDisplay;
	private static JButton fetchButton;
	private static String apikey ="e42af5addda96838639e4601f2f16021";
	private static ArrayList weatherArray;
	
	private static String fetchWeatherData(String city) {
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apikey);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line ;
			while((line = reader.readLine()) !=null)	{
				response += line;
			}
			reader.close();
			
			JSONObject jsonobject = (JSONObject) JSONValue.parse(response.toString());
			JSONObject mainObj =(JSONObject )jsonobject.get("main");	
			double temperatureKelvin =(double)mainObj.get("temp");
			long humidity = (long)mainObj.get("humidity");
			double temperaturcelcius = temperatureKelvin - 273.15;
			//retrive weather description
			JSONArray weatheraArray = (JSONArray)jsonobject.get("weather");
		    JSONObject weather = (JSONObject)weatheraArray.get(0);
			String description = (String) weather.get("description");
			
			return "description: " + description + "/ntemperature:" + temperaturcelcius +
					"celsius/nHumidity:" + humidity + "%";
		} catch (Exception e) {
			return "Failed to fetch weather data";
			
		}
		
	}

	public static void main(String[]args) {
		
	    frame = new JFrame ("Weather forecast");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setLayout(new FlowLayout());
		
	     JTextField loctionField = new JTextField(15);
		fetchButton = new JButton("Fetch Weather");
	    weatherDisplay = new JTextArea(10,30);
		weatherDisplay.setEditable(false);
		
		frame.add(new JLabel("Enter city Name"));
		frame.add(loctionField);
		frame.add(fetchButton);
		frame.add(weatherDisplay);
		
          fetchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				
				String city = loctionField.getText();
				String weatherInfo = fetchWeatherData(city);
				weatherDisplay.setText(weatherInfo);
			
				
			}
		});
			
		
		frame.setVisible(true);
		
		
	}
}
