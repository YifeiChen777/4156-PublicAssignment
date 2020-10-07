package controllers;

import com.google.gson.Gson;
import io.javalin.Javalin;
import java.io.IOException;
import java.util.Queue;
import models.GameBoard;
import models.Message;
import models.Move;
import models.Player;
import org.eclipse.jetty.websocket.api.Session;

class PlayGame {

  private static final int PORT_NUMBER = 8080;

  private static Javalin app;

  /** Main method of the application.
   * @param args Command line arguments
   */
  public static void main(final String[] args) {

    app = Javalin.create(config -> {
      config.addStaticFiles("/public");
    }).start(PORT_NUMBER);

    // Test Echo Server
    app.post("/echo", ctx -> {
      ctx.result(ctx.body());
    });

    GameBoard gameBoard = new GameBoard();
    
    app.get("/newgame", ctx -> {
      gameBoard.startOver();
      ctx.redirect("/tictactoe.html");
    });
    
    app.post("/startgame", ctx -> {
      String type = ctx.formParam("type");
      if (type.equals("X")) {
        gameBoard.setPlayer1('X');
      } else {
        gameBoard.setPlayer1('O');
      }
      Gson gson = new Gson();
      ctx.result(gson.toJson(gameBoard));
    });
    
    app.get("/joingame", ctx -> {
      if (gameBoard.getPlayer1().getType() == 'X') {
        gameBoard.setPlayer2('O');
      } else {
        gameBoard.setPlayer2('X');
      }
      gameBoard.startGame();
      Gson gson = new Gson();
      sendGameBoardToAllPlayers(gson.toJson(gameBoard));
      ctx.redirect("/tictactoe.html?p=2");
    });
    
    app.post("/move/:playerId", ctx -> {
      int id = Integer.parseInt(ctx.pathParam("playerId"));
      int x = Integer.parseInt(ctx.formParam("x"));
      int y = Integer.parseInt(ctx.formParam("y"));
      Player player;
      if (id == 1) {
        player = gameBoard.getPlayer1();
      } else {
        player = gameBoard.getPlayer2();
      }
      Move move = new Move(player, x, y);
      boolean result = gameBoard.tryMove(move);
      Message msg;
      if (result) {
        msg = new Message(true, 100, "");
      } else {
        msg = new Message(false, 100, "Invalid move");
      }
      Gson gson = new Gson();
      ctx.result(gson.toJson(msg));
      sendGameBoardToAllPlayers(gson.toJson(gameBoard));
    });

    // Web sockets - DO NOT DELETE or CHANGE
    app.ws("/gameboard", new UiWebSocket());
  }

  /** Send message to all players.
   * @param gameBoardJson Gameboard JSON
   * @throws IOException Websocket message send IO Exception
   */
  private static void sendGameBoardToAllPlayers(final String gameBoardJson) {
    Queue<Session> sessions = UiWebSocket.getSessions();
    for (Session sessionPlayer : sessions) {
      try {
        sessionPlayer.getRemote().sendString(gameBoardJson);
      } catch (IOException e) {
        // Add logger here
        System.out.println("Error with sending to players.");
      }
    }
  }

  public static void stop() {
    app.stop();
  }
}
