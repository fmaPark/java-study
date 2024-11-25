import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; 
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("서버에 연결되었습니다!");

            // 데이터 송수신용 스트림 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 사용자 입력 스트림
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("채팅을 시작하세요 (exit 입력 시 종료):");

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message); // 서버로 메시지 전송
                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("연결을 종료합니다.");
                    break;
                }
                // 서버로부터 응답 출력
                String response = in.readLine();
                System.out.println(response);
            }

            System.out.println("클라이언트를 종료합니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
