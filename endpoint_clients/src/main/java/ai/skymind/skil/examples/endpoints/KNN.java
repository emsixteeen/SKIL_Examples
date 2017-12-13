package ai.skymind.skil.examples.endpoints;

public class KNN {

    private String host;
    private String port;

    public KNN() {
        this.host = "localhost";
        this.port = "9008";
    }

    public KNN(String host, String port) {
        this.host = host;
        this.port = port;
    }

}
