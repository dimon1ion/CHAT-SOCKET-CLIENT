import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    static Socket clientSocket;
    static BufferedReader reader;
    static BufferedReader in;
    static BufferedWriter out;
    static boolean isWork = true;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 28822);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Thread rm = new Thread(()-> {
                    String text;
                    try {
                        while(Main.isWork) {
                            text = Main.in.readLine();
                            if(text.equals("stop")) {
                                break;
                            }
                            System.out.println(text);
                        }
                    } catch (IOException e) {

                    }
                    finally {
                        Main.isWork = false;
                    }
                });

                Thread wm = new Thread(()->{
                    while(Main.isWork) {
                        String userText;
                        try {
                            userText = Main.reader.readLine();
                            Main.out.write(userText + "\n");
                            Main.out.flush();
                        } catch (IOException e) {

                        }
                    }
                });

                rm.start();
                wm.start();

                rm.join();
                wm.join();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
