package client.net;

import client.engine.Game;
import common.*;
import common.Point;
import server.GameMessage;
import server.GameMessageType;
import server.messages.GameAnswerMessage;
import server.messages.GameJoinMessage;
import server.messages.GameSetupMessage;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Client implements Runnable{
    private static final Logger log = Logger.getLogger( Client.class.getName());

    public static int PORT = 8000;
    private Socket socket;
    private String serverAddress;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    Game game;

    public Client(String serverAddress, Game game) {
      this.serverAddress = serverAddress;
      this.game = game;

      try {
        socket = new Socket(serverAddress, PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        log.info("creating socket I/O");
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        log.info("Done creating socket I/O");

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //TODO:
    @Override
    public void run() {
      while(true) {

      }
    }

    //TODO:
    public boolean canMove(Point from, Point to) {


      return false;
    }

    public boolean canStartNewGame(int numberOfPlayers) {
      sendNewGameMessage(numberOfPlayers);
      GameAnswerMessage msg = readNewAnswerMessage();
      log.info(msg.getDesc() + ", " + msg.getAnswer());
      if(msg != null && msg.getAnswer() == true) {
        return true;
      }
      return false;
    }

    private void sendNewGameMessage(int numberOfPlayers) {
      GameSetupMessage msg = new GameSetupMessage(BoardType.BOARD_STAR, numberOfPlayers);
      try {
        objectOutputStream.writeObject(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void sendGameMessage(GameMessage msg) {
      try {
        objectOutputStream.writeObject(msg);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private GameAnswerMessage readNewAnswerMessage() {
      GameMessage msg = null;
      try {
        msg = (GameMessage) objectInputStream.readObject();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      if (msg.getGameMessageType() == GameMessageType.GAME_ANSWER_MESSAGE)
        return (GameAnswerMessage) msg;
      else
        return null;
    }

    //TODO:
    public boolean canJoinGame(int numberOfPlayers) {
      sendGameMessage(new GameJoinMessage(BoardType.BOARD_STAR, numberOfPlayers));
      GameAnswerMessage msg = readNewAnswerMessage();
      log.info(msg.getDesc() + ", " + msg.getAnswer());
      if(msg != null && msg.getAnswer() == true) {
        return true;
      }
      return false;
    }
}
