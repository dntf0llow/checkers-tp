package server;

import client.PlayerTag;
import server.messages.GameLogMessage;
import server.messages.GameStatusMessage;
import server.messages.GameTurnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread {
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;
  private Game game;
  private Socket socket;
  private boolean running;
  private PlayerTag tag;

  public Player(Socket socket) throws IOException {
    this.socket = socket;
    try {
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectInputStream = new ObjectInputStream(socket.getInputStream());

    } catch (IOException e) {
      e.printStackTrace();
      throw new IOException(String.format("Player init failed with %s", e.getMessage()));
    }
  }

  public void run() {
    running = true;
    System.out.println("Player tag: " + tag.toString());
    writeGameMessage(new GameStatusMessage(tag));
    writeGameMessage(new GameLogMessage("All players connected!"));


    while (running) {
      GameMessage msg = readGameMessage();
      handleGameMessage(msg);
    }
  }

  void setGame(Game game) {
    this.game = game;
  }
  void setPlayerTag(PlayerTag tag) { this.tag = tag; }

  //TODO:
  private void handleGameMessage(GameMessage msg) {
    switch(msg.getGameMessageType()) {
      case GAME_MOVEMENT_MESSAGE:
        game.broadcastMoveMessage(msg, tag);
        break;
      case GAME_LOG_MESSAGE:
        System.out.println(((GameLogMessage) msg).getDesc());
        break;
    }
  }

  public GameMessage readGameMessage() {
    GameMessage msg = null;
    try {
      msg = (GameMessage) objectInputStream.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return msg;
  }

  public void writeGameMessage(GameMessage msg) {
    try {
      objectOutputStream.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PlayerTag getTag() {
    return tag;
  }
}
