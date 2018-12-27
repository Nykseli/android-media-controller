package stream.xlinuxtools.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static android.content.ContentValues.TAG;

public class WebSocket extends WebSocketClient{

    private static WebSocket webSocket;

    private static SocketCommands sc;


    public WebSocket(URI serverUri) {
        super(serverUri);
        sc = new SocketCommands();

    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: " + handshakedata.toString());
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendCommand(Commands command){

        String commandString = null;

        switch (command){
            case LEFT_CLICK:
                commandString = sc.mouseLeftClick();
                break;
            case MOUSE_UP:
                commandString = sc.mouseUp();
                break;
            case MOUSE_DOWN:
                commandString = sc.mouseDown();
                break;
            case MOUSE_LEFT:
                commandString = sc.mouseLeft();
                break;
            case MOUSE_RIGHT:
                commandString = sc.mouseRight();
                break;
            case SKIP_NETFLIX_INTRO:
                commandString = sc.skipNetflixIntro();
                break;
            default:
                break;
        }

        try {
            this.send(commandString);
        }catch (Exception err){
            //TODO: alert user that socket.send doesn't work
            err.printStackTrace();
        }
    }
}
