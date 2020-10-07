package controllers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import kong.unirest.HttpResponse;
import kong.unirest.*;
import kong.unirest.json.JSONObject;
import models.GameBoard;
import com.google.gson.Gson;
import models.Player;
import models.Message;


@TestMethodOrder(OrderAnnotation.class) 
public class PlayGameTest {
	
  /**
   * Runs only once before the testing starts.
  */
  @BeforeAll
  public static void init() {
    // Start Server
    PlayGame.main(null);
    System.out.println("Before All");
  }
	
  /**
  * This method starts a new game before every test run. It will run every time before a test.
  */
  @BeforeEach
  public void startNewGame() {
    // Test if server is running. You need to have an endpoint /
    // If you do not wish to have this end point, it is okay to not have anything in this method.
    HttpResponse<String> response = Unirest.get("http://localhost:8080/").asString();
    int restStatus = response.getStatus();
    assertEquals(restStatus, 200);
    System.out.println("Before Each");
  }
	
  /**
  * This is a test case to evaluate the newgame endpoint.
  */
  @Test
  @Order(1)
  public void newGameTest() {
  	
    // Create HTTP request and get response
    HttpResponse<String> response = Unirest.get("http://localhost:8080/newgame").asString();
    int restStatus = response.getStatus();
        
    // Check assert statement (New Game has started)
    assertEquals(restStatus, 200);
    System.out.println("Test New Game");
  }
    
  /**
  * This is a test case to evaluate the startgame endpoint.
  */
  @Test
  @Order(2)
  public void startGameTest() {
    	
    // Create a POST request to startgame endpoint and get the body
    // Remember to use asString() only once for an endpoint call. 
    // Every time you call asString(), a new request will be sent to the endpoint. 
    //Call it once and then use the data in the object.
    HttpResponse<String> response = Unirest.post("http://localhost:8080/startgame").body("type=X").asString();
    String responseBody = response.getBody();
        
    // --------------------------- JSONObject Parsing ----------------------------------
        
    System.out.println("Start Game Response: " + responseBody);
        
    // Parse the response to JSON object
    JSONObject jsonObject = new JSONObject(responseBody);

    // Check if game started after player 1 joins: Game should not start at this point
    assertEquals(false, jsonObject.get("gameStarted"));
        
    // ---------------------------- GSON Parsing -------------------------
        
    // GSON use to parse data to object
    Gson gson = new Gson();
    GameBoard gameBoard = gson.fromJson(jsonObject.toString(), GameBoard.class);
    Player player1 = gameBoard.getPlayer1();
        
    // Check if player type is correct
    assertEquals('X', player1.getType());
        
    System.out.println("Test Start Game");
  }
  
  /**
  * This is a test case to evaluate the joingame endpoint.
  */
  @Test
  @Order(3)
  public void joinTest() {
    HttpResponse<String> response = Unirest.get("http://localhost:8080/joingame").asString();
    int restStatus = response.getStatus();
    assertEquals(restStatus, 200);
    System.out.println("Test Join Game");
  }
   
  /**
  * This is a test case to evaluate the move endpoint.
  */
  @Test
  @Order(4)
  public void moveTest() {
    HttpResponse<String> response = Unirest.post("http://localhost:8080/move/1").body("x=0&y=0").asString();
    String responseBody = response.getBody();
    System.out.println("Move Response: " + responseBody);
    JSONObject jsonObject = new JSONObject(responseBody);
    assertEquals(true, jsonObject.get("moveValidity"));
    assertEquals(100, jsonObject.get("code")); 
    
    HttpResponse<String> r1 = Unirest.post("http://localhost:8080/move/2").body("x=1&y=1").asString();
    String rb1 = r1.getBody();
    System.out.println("Move Response: " + rb1);
    JSONObject jsonObject1 = new JSONObject(rb1);
    assertEquals(true, jsonObject1.get("moveValidity"));
    assertEquals(100, jsonObject1.get("code")); 
    System.out.println("Test Move");
    
    HttpResponse<String> r2 = Unirest.post("http://localhost:8080/move/1").body("x=1&y=1").asString();
    String rb2 = r2.getBody();
    System.out.println("Move Response: " + rb2);
    JSONObject jsonObject2 = new JSONObject(rb2);
    assertEquals(false, jsonObject2.get("moveValidity"));
    assertEquals(100, jsonObject2.get("code")); 
    System.out.println("Test Move");
  }
  
  @Test
  @Order(5)
  public void otherTest() {
    HttpResponse<String> r1 = Unirest.get("http://localhost:8080/newgame").asString();
    int restStatus = r1.getStatus();        
    assertEquals(restStatus, 200);
    HttpResponse<String> r2 = Unirest.post("http://localhost:8080/startgame").body("type=O").asString();
    String responseBody = r2.getBody();
    System.out.println("Start Game Response: " + responseBody);
    JSONObject jsonObject = new JSONObject(responseBody);
    assertEquals(false, jsonObject.get("gameStarted"));
    Gson gson = new Gson();
    GameBoard gameBoard = gson.fromJson(jsonObject.toString(), GameBoard.class);
    Player player1 = gameBoard.getPlayer1();
    assertEquals('O', player1.getType());
    HttpResponse<String> response = Unirest.get("http://localhost:8080/joingame").asString();
    int rs = response.getStatus();
    assertEquals(rs, 200);
  }
    
  /**
  * This will run every time after a test has finished.
  */
  @AfterEach
  public void finishGame() {
    System.out.println("After Each");
  }
    
  /**
  * This method runs only once after all the test cases have been executed.
  */
  @AfterAll
  public static void close() {
    // Stop Server
    PlayGame.stop();
    System.out.println("After All");
  }
}

