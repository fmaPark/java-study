import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        int port = 5000;


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("채팅 서버가 포트 " + port + "에서 시작되었습니다.");
            System.out.println("클라이언트를 기다리는 중...");
            
            Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 대기
            System.out.println("클라이언트가 연결되었습니다.");

            // 스트림 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("클라이언트: " + clientMessage);
                if ("exit".equalsIgnoreCase(clientMessage)) {
                    System.out.println("클라이언트가 연결을 종료했습니다.");
                    break;
                }
                out.println("서버: " + clientMessage);
            }

            // 클라이언트와 연결 종료
            clientSocket.close();
            System.out.println("서버가 종료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
