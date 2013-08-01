package com.xenon.greenup.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

public class APIServerInterface {
	
	
	private final String BASE_URL = "https://greenupapp.appspot.com/api";
	
	//Class to serve as the main interface to the API server
	//The application will invoke methods defined here to perform 
	// the various requests
	
	//TODO: Write private methods to parse and create JSON objects
	//TODO: Make this handle different HTTP response codes properly beyond throwing an exception and exploding
	
	
	//Retrieves a page of comments from the server, the type and page number are optional
	public CommentPage getComments(String type, int page) {
		return new CommentPage();
	}
	
	//Submits a comment, the pin is optional.  Returns an integer status code (codes TBD)
	public int submitComments(String type, String message, int pin) {
		return 0;
	}
	
	//Get a list of heatmap points for the specified coordinates, all parameters are optional (??)
	public Heatmap getHeatmap(float latDegress, float latOffset, float lonDegrees, float lonOffset, int precision){
		return new Heatmap();
	}
	
	//Submit a heatmap point
	public int submitHeatmapPoint(float latDegrees, float lonDegrees, int secondsWorked){
		return 0;
	}
	
	//Get a list of pins, all parameters are optional
	public PinList getPins(float latDegrees, float latOffset, float lonDegrees, float lonOffset){
		return new PinList();
	}
	
	//Submit a pin
	public int submitPin(float latDegrees, float lonDegrees, String type, String message){
		return 0;
	}
	
	public int testConnection(){
		String response;
		APIRequestTask request = new APIRequestTask(BASE_URL);
		request.execute();
		try {
			response = request.get();
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			response = "Error";
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			response = "Error";
		}
		Log.i("response",response);
		return 0;
	}
	
	private class APIRequestTask extends AsyncTask<Void,Void,String>{
		
		private String url;
		private String data;
		private boolean isPOST;
		
		//Constructor for GET requests
		public APIRequestTask(String url) {
			this.url = url;
			this.isPOST = false;
		}
		
		//Constructor for POST requests
		public APIRequestTask(String url,String data) {
			this.url = url;
			this.data = data;
			this.isPOST = true;
		}
		
		//Where the magic happens...
		@Override
		protected String doInBackground(Void...voids) {
			String response;
			URL urlObject;
			try {
				urlObject = new URL(url);
				HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
				if (isPOST) {
					connection.setDoOutput(isPOST);
					int length = data.length();
					connection.setFixedLengthStreamingMode(length);
					OutputStream out = connection.getOutputStream();
					out.write(data.toString().getBytes());
				}
				InputStreamReader reader = new InputStreamReader(connection.getInputStream());
				StringBuilder sb = new StringBuilder();
				
				int readByte;
				while ((readByte = reader.read()) != -1) {
					sb.append((char)readByte);
				}
				response = sb.toString();
			}
			catch(IOException e) {
				e.printStackTrace();
				response = "Error occurred, see stack trace";
			}
			return response;
		}
	}
	
	
	
	
	
	
	
	
}