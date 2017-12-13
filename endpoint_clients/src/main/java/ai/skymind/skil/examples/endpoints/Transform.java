package ai.skymind.skil.examples.endpoints;

public class Transform {

    private String host;
    private String port;

    public Transform() {
        this.host = "localhost";
        this.port = "9008";
    }

    public Transform(String host, String port) {
        this.host = host;
        this.port = port;
    }

}
