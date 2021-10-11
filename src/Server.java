import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import javax.crypto.Cipher;

public class Server {

    private final String ip = "localhost";
    private final int port = 8080;
    private static HashMap<String, String> id_pass = new HashMap<String, String>();
    private KeyPair decrPair;

    public static void main(String[] args) throws Exception {
    	System.out.println("Server running");
        ServerSocket serverSocket = new ServerSocket(8080);

        try {
            while (true){
                Socket socket = serverSocket.accept();
                startHandler(socket);
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void startHandler(final Socket socket) throws Exception{

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
//                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                    //String line = reader.readLine();
                    //JSONObject jsonObject = new JSONObject(line);

//                    writer.write(jsonObject.toString() + "\n");
//                    writer.flush();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String[] data = (String[]) ois.readObject();

//                    CipherSample cipher = new CipherSample(data[0], data[1]);
//
//

                    if(checkIfIdAlreadyExist(data[0], data[1])){
                        System.out.println("ERROR");
                    }
                    else {
                        generateJSON(data);
                    }

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    closeSocket();
                }

            }
            private void closeSocket () {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        };

        thread.start();

    }

    private static void takeAction(int steps, int seconds, Counter counter) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
        counter.increase(steps);
        System.out.println("counter value:" + counter.getValue());
    }

    private static boolean checkIfIdAlreadyExist(String id, String pwd){
        if(id_pass.containsKey(id)){
            System.out.println("this id already exists");
            return true;
        }
        id_pass.put(id, pwd);
        return false;
    }

    private static JSONObject generateJSON(String[] data) throws InterruptedException {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonServer = new JSONObject();
        JSONObject jsonAction = new JSONObject();

        jsonObject.put("id", data[0]);
        jsonObject.put("password", data[1]);

        jsonServer.put("ip", "localhost");
        jsonServer.put("port", 8080);

        int delay = (int) (Math.random() *10) + 1;
        jsonAction.put("delay", delay);
        int[] action = new int[]{(int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1,
                (int) (Math.random() * 10) + 1};
        jsonAction.put("steps", action);

        jsonObject.put("server", jsonServer);
        jsonObject.put("actions", jsonAction);

        Counter counter = new Counter();
        System.out.println(jsonObject);

//        JSONArray data_file = new JSONArray();
//        data_file.put(jsonObject);

        //Write JSON file
        try (FileWriter file = new FileWriter((data[0] + ".json"))) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonObject.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < action.length; i++){
            takeAction(action[i],delay, counter);
            File file = new File((data[0] + ".json"));
            if (file.exists()){
                file.delete();
            }
        }
        return jsonObject;
    }



}