import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.sql.*;

public class SearchTweets {

	public static int tweetCount =0;
	
	//Twitter streaming function
	public void streamTweets() {
	ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("UYleS1XoOv2FWdRK1wC5KGGOu");
        cb.setOAuthConsumerSecret("A2cgxJoqilO8TdaLV9BYcngeIRbiHYjgYUtFh2lUtv2lEU056M");
        cb.setOAuthAccessToken("959040167775412224-ht6JjhL8NPaH9WPqpnh99v8QDt28wYA");
        cb.setOAuthAccessTokenSecret("en7e5kGYYX2zD5zZa0SprhjoHzy1MGFhBo6R6I7fwkknd");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        StatusListener listener = new StatusListener() {
        	
        	@Override
        	public void onStallWarning(twitter4j.StallWarning warning){
        		// TODO Auto-generated method stub
            }
        	
            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }

            //This function is for the streaming of tweets
            //using keywords found in the array.
            @Override
            public void onStatus(Status status) {
            	//Increment the amount of tweets crawled
            	tweetCount ++;
            	
            	User user = status.getUser();
            	
            	//Increment the amount of tweets crawled
                System.out.println(tweetCount);
            	
                // Parsing JSON data
                String username = status.getUser().getScreenName();
                System.out.println("Username: " + username);
                
                // Get location
                String profileLocation = user.getLocation();
                System.out.println("Location: " + profileLocation);
                
                // Get tweet ID
                long tId = status.getId(); 
                System.out.println("Tweet ID: " +tId);
                
                // Get tweet contents
                String content = status.getText();
                System.out.println("Content: " + content);
                
                // Get time tweet posted
                java.util.Date timeStamp = status.getCreatedAt();
                System.out.println("Timestamp: " + timeStamp +"\n");
               
                try {
        			//Establish connection
        			String driver = "com.mysql.jdbc.Driver";
        		    String url    = "jdbc:mysql://localhost:3306/twitter";
        		    String dbusername = "root";
        		    String password = "";
        		    System.setProperty(driver,"");
        			Connection myConn = DriverManager.getConnection(url,dbusername,password);
        			Statement myStat = myConn.createStatement();
        			
        			if (content.contains("'") == false) {
        				//SQL query to insert
            			String sql = "INSERT into tweets"
            					+ "(`username`, `location`, `content`)"
            					+ "values('"+username+"', '"+profileLocation+"', '"+content+"')";
            			
            			myStat.executeUpdate(sql);
            			
            			System.out.println("Update complete\n");		
        			}
        		}
        		catch (Exception exc) {
        			exc.printStackTrace();
        		}
            }
        };
        
        FilterQuery fq = new FilterQuery();
        
        String keywords[] = {"cloverfield"};
        
        fq.track(keywords);
        //fq.language("en");

        twitterStream.addListener(listener);
        twitterStream.filter(fq); 
	}
	
	//Main function
    public static void main(String[] args) {  
    	SearchTweets tweetsObj = new SearchTweets();
    	tweetsObj.streamTweets();
    }
}

