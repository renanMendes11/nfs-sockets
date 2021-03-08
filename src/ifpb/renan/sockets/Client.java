package ifpb.renan.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==");

        // configurando o socket
        Socket socket = new Socket("127.0.0.1", 7002);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do cliente
        while (true) {
            Scanner teclado = new Scanner(System.in);
            // escrevendo para o servidor
            dos.writeUTF(teclado.nextLine());

            // lendo o que o servidor enviou
            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: " + mensagem);
        }
    }
}