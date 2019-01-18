package stream.mediacontroller.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.nio.ByteBuffer;

import stream.mediacontroller.MainActivity;
import stream.mediacontroller.command.AudioCommand;
import stream.mediacontroller.command.ConfigCommand;
import stream.mediacontroller.command.GeneralCommand;
import stream.mediacontroller.command.KeyboardCommand;
import stream.mediacontroller.command.MouseCommand;
import stream.mediacontroller.command.VlcCommands;
import stream.mediacontroller.crypto.Crypto;
import stream.mediacontroller.util.PreferenceStorage;

import static android.content.ContentValues.TAG;

public class WebSocket extends WebSocketClient{

    public static WebSocketDataGetter dataGetter = null;

    public WebSocket(URI serverUri, WebSocketDataGetter dGetter){
        super(serverUri);
        dataGetter = dGetter;
    }

    public WebSocket(URI serverUri) {
        super(serverUri);
    }

    /**
     * Try to reconnect to socket on new thread to avoid blockage of bad connection.
     */
    public void asyncReConnect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                reconnect();
            }
        }).run();
    }

    /**
     * Try to reconnect to socket on new thread to avoid blockage of bad connection.
     */
    public void asyncConnect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }).run();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: " + handshakedata.toString());
        sendCommand(Commands.CONFIG_GET_CONFIG, null);
        MainActivity.infoPopUp.showShortMessage("Web Socket connected");
    }

    /**
     * Parse plain text messages from server
     * @param message
     */
    @Override
    public void onMessage(String message) {
        dataGetter.parseJson(message);
    }

    /**
     * Decrypt and parse encrypted message from server
     * @param blob encrypted message
     */
    @Override
    public void onMessage(ByteBuffer blob){
        byte[] encodedArray = blob.array();
        Log.d(TAG, "onMessage: we have o blob length: " + encodedArray.length);
        String decodedMessage = Crypto.decryptCFB(encodedArray);
        dataGetter.parseJson(decodedMessage);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: " + reason);
        MainActivity.infoPopUp.showShortMessage("Web Socket connection closed");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * sendCommand creates message that is send to server based on Commands
     * @param command Command that you want to use. Full list in websocket.Commands
     * @param additionalInfo JSONObject that contains additional info to be send to server. Use null if command doesn't need any additional info
     */
    public void sendCommand(Commands command, JSONObject additionalInfo){

        String commandString = null;

        switch (command){
            // Audio
            case AUDIO_INCREASE_MASTER_VOLUME:
                commandString = AudioCommand.increaseMasterVolume();
                break;
            case AUDIO_DECREASE_MASTER_VOLUME:
                commandString = AudioCommand.decreaseMasterVolume();
                break;

            // Config
            case CONFIG_GET_CONFIG:
                commandString = ConfigCommand.getConfig();
                break;

            // General
            case GENERAL_GET_FILES_AND_FOLDERS:
                // Additional info is the absolute path of the folder that we want to browse
                commandString = GeneralCommand.getFilesAndFolders(additionalInfo);
                break;

            // Mouse
            case MOUSE_LEFT_CLICK:
                commandString = MouseCommand.leftMouseClick();
                break;
            case MOUSE_MOUSE_UP:
                commandString = MouseCommand.mouseUp();
                break;
            case MOUSE_MOUSE_DOWN:
                commandString = MouseCommand.mouseDown();
                break;
            case MOUSE_MOUSE_LEFT:
                commandString = MouseCommand.mouseLeft();
                break;
            case MOUSE_MOUSE_RIGHT:
                commandString = MouseCommand.mouseRight();
                break;
            case MOUSE_SKIP_NETFLIX_INTRO:
                commandString = MouseCommand.skipNetflixIntro();
                break;
            case MOUSE_NEXT_NETFLIX_EPISODE:
                commandString = MouseCommand.nextNetflixEpisode();
                break;
            case MOUSE_REWIND_NETFLIX_BACKWARD:
                commandString = MouseCommand.rewindNetflixBackward();
                break;
            case MOUSE_FAST_NETFLIX_FORWARD:
                commandString = MouseCommand.fastNetflixForward();
                break;

            // Keyboard
            case KEYBOARD_LOGIN:
                commandString = KeyboardCommand.login();
                break;

            // Vlc
            case VLC_PLAY_FILE:
                // Additional info is the absolute path of the file we want to play
                commandString = VlcCommands.playFile(additionalInfo);
                break;
            case VLC_PLAY_FILES:
                // Additional info contains list of files we want to play
                commandString = VlcCommands.playFiles(additionalInfo);
                break;
            case VLC_PAUSE_FILE:
                commandString = VlcCommands.pauseFile();
                break;
            case VLC_PLAY_NEXT_MEDIA:
                commandString = VlcCommands.playNextMedia();
                break;
            case VLC_PLAY_PREVIOUS_MEDIA:
                commandString = VlcCommands.playPreviousMedia();
                break;
            case VLC_INCREASE_VOLUME:
                commandString = VlcCommands.increaseVolume();
                break;
            case VLC_DECREASE_VOLUME:
                commandString = VlcCommands.decreaseVolume();
                break;
            case VLC_MUTE_VOLUME:
                commandString = VlcCommands.muteVolume();
                break;
            case VLC_FAST_FORWARD:
                commandString = VlcCommands.fastForward();
                break;
            case VLC_REWIND:
                commandString = VlcCommands.rewind();
                break;

            default:
                break;
        }

        try {
            // If encryption is enabled in settings, send message as encrypted
            if(PreferenceStorage.SECURITY_IS_ENCRYPTION_ENABLED){
                byte[] message = Crypto.cryptCFB(commandString);
                this.send(message);
            }else {
                this.send(commandString);
            }
        }catch (Exception err){
            //TODO: alert user that socket.send doesn't work
            err.printStackTrace();
            MainActivity.infoPopUp.showShortMessage("Web Socket is not connected!");
        }
    }
}
