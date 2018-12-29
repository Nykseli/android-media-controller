package stream.xlinuxtools.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static android.content.ContentValues.TAG;

public class WebSocket extends WebSocketClient{

    private static WebSocket webSocket;

    private static SocketCommands sc = new SocketCommands();
    private static WebSocketDataGetter dataGetter = null;


    public WebSocket(URI serverUri, WebSocketDataGetter dGetter){
        super(serverUri);
        dataGetter = dGetter;
    }

    public WebSocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: " + handshakedata.toString());
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: " + message);
        dataGetter.parseJson(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendCommand(Commands command, String additionalInfo){

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
            case NEXT_NETFLIX_EPISODE:
                commandString = sc.nextNetflixEpisode();
                break;
            case REWIND_NETFLIX_BACKWARD:
                commandString = sc.rewindNetflixBackward();
                break;
            case FAST_NETFLIX_FORWARD:
                commandString = sc.fastNetflixForward();
                break;
            case GET_FILES_AND_FOLDERS:
                // Additional info is the absolute path of the folder that we want to browse
                commandString = sc.getFilesFromFolder(additionalInfo);
                break;
            case PLAY_FILE:
                // Additional info is the absolute path of the file we want to play
                commandString = sc.playFile(additionalInfo);
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
