package com.unsa.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;


public class MainTest {
	
	
	
	/** Global instance properties filename. */
	  private static String PROPERTIES_FILENAME = "youtube.properties";

	  /** Global instance of the HTTP transport. */
	  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	  /** Global instance of the JSON factory. */
	  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	  /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
	  private static final long NUMBER_OF_VIDEOS_RETURNED = 9;

	  /** Global instance of Youtube object to make all API requests. */
	  private static YouTube youtube;
	  
	 public static void main(String[] args) {
    	
      	
   	 // *****************************************************************************
       OpenOWL owl = new OpenOWL ("/home/jhoedmon/workspace/home/src/main/java/com/unsa/home/ambiental.rdf");
       String wordToSearch = "";
       //System.out.println(queryTerm+"******************");
       List<String> s = owl.getSubClasses("Contaminante");
       
       for (int i=0;i<s.size();i++){
           System.out.println(s.get(i));
           wordToSearch += s.get(i) +" ";
       }
       System.out.println(wordToSearch);
       // ***************************************************************************** 
      
    	List<String> expansion = new ArrayList<>();
    	
       
       Properties properties = new Properties();
	    try {
	      InputStream in = Search.class.getResourceAsStream(PROPERTIES_FILENAME);
	      properties.load(in);

	    } catch (IOException e) {
	      System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
	          + " : " + e.getMessage());
	      System.exit(1);
	    }
	    
	    try {
		      /*
		       * The YouTube object is used to make all API requests. The last argument is required, but
		       * because we don't need anything initialized when the HttpRequest is initialized, we override
		       * the interface and provide a no-op function.
		       */
		      youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
		        public void initialize(HttpRequest request) throws IOException {}
		      }).setApplicationName("youtube-cmdline-search-sample").build();

		      // Get query term from user.
		      
		      YouTube.Search.List search = youtube.search().list("id,snippet");
		      String apiKey = properties.getProperty("youtube.apikey");
		      search.setKey(apiKey);

			for (String queryTerm : expansion) {
				search.setQ(queryTerm);
				search.setType("video");
				search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet/description,snippet/channelTitle)");
				search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
				SearchListResponse searchResponse = search.execute();

				List<SearchResult> searchResultList = searchResponse.getItems();
				if (searchResultList != null) {
					//req.setAttribute("datos", searchResultList);
					for (SearchResult s1 : searchResultList ){
						System.out.println(s1.toString());
					}
//					req.setAttribute("search", num);
//					req.getRequestDispatcher("guestbook.jsp")
//							.forward(req, resp);
				}
			}


	    } catch (GoogleJsonResponseException e) {
	      System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	          + e.getDetails().getMessage());
	    } catch (IOException e) {
	      System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	    } catch (Throwable t) {
	      t.printStackTrace();
	    }
	    
	}
    public static  List<String> expansion(String terminos) {
		// *****************************************************************************
	       OpenOWL owl = new OpenOWL ("/home/jhoedmon/workspace/home/src/main/java/com/unsa/home/ambiental.rdf");
	       //System.out.println(queryTerm+"******************");
	       List<String> s = owl.getSubClasses(terminos);
	       return s;
	}
}
