package stream.xlinuxtools.websocket;

public class SocketCommands {

    // defines default mouse movement in pixels
    private static final String PIXEL_AMOUNT = "10";
    private static final String NEGATIVE_PIXEL_AMOUNT = "-" + PIXEL_AMOUNT;


    public String mouseUp(){
        return "{\"command\": \"moveMouseY\", \"amount\": "+NEGATIVE_PIXEL_AMOUNT+" }";
    }

    public String mouseDown(){
        return "{\"command\": \"moveMouseY\", \"amount\": "+PIXEL_AMOUNT+" }";
    }

    public String mouseLeft(){
        return "{\"command\": \"moveMouseX\", \"amount\": "+NEGATIVE_PIXEL_AMOUNT+" }";
    }

    public String mouseRight(){
        return "{\"command\": \"moveMouseX\", \"amount\": "+PIXEL_AMOUNT+" }";
    }

    public String mouseLeftClick(){
        return  "{\"command\": \"leftMouseClick\"}";
    }

    public String skipNetflixIntro(){
        return "[{\"command\": \"setMousePosition\", \"x\": 1748, \"y\": 884 }," + this.mouseLeftClick() + "]";
    }

}
