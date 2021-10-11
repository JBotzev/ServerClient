import netscape.javascript.JSObject;

import java.io.*;
import java.net.Socket;

import org.json.JSONObject;

public class Client {

    private String id;
    private String password;
    private final String ip = "localhost";
    private final int port = 8080;

    public Client(String id, String password){
        this.id = id;
        this.password = password;
    }

    public void run() throws Exception {
        Socket socket = new Socket("localhost", this.port);
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

//      writer.write(jsonObject.toString() + "\n");
//      writer.flush();

        String[] idData = new String[]{this.id, this.password};

        CipherRSA rsa = new CipherRSA();
//        rsa.init();
        rsa.initFromStrings();
        String encrID = rsa.encrypt(this.id);
        String encrPass = rsa.encrypt(this.password);


//        CipherSample cipher = new CipherSample(this.id, this.password);
//        cipher.initFromStrings();
//        cipher.encrypt();
        String[] encrData = new String[] {encrID, encrPass};

        System.out.println("SENDING DATA");
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(encrData);
        oos.flush();

        // String line = reader.readLine();
        // jsonObject = new JSONObject(line);
        // System.out.println("Recieved from Server:\n" + jsonObject.toString(2));
        socket.close();
    }
}
