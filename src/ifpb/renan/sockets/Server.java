package ifpb.renan.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7002);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());
            String comando = dis.readUTF();
            String[] splitedCommand = comando.split(" ");

            switch (splitedCommand[0]) {
                case "readdir":
                    dos.writeUTF("Lendo diretório: " + readdir(splitedCommand[1]));break;
                case "rename":
                    dos.writeUTF(rename(splitedCommand[1], splitedCommand[2]));break;
                case "create":
                    dos.writeUTF(create(splitedCommand[1]));break;
                case "remove":
                    dos.writeUTF(remove(splitedCommand[1]));break;
            }
        }

    }
    public static String readdir(String dir){
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        String result = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            result += listOfFiles[i].getName() + " ";
        }
        return result;
    }

    public static String rename(String old,String newArq) {
        Path source = Paths.get(old);
        try {
            Path result = Files.move(source, source.resolveSibling(newArq));
            return "Renomeado para " + newArq;
        } catch (IOException e) {
            return "arquivo de origem não existe";
        }
    }

    public static String create(String name) throws IOException {
        File file = new File(name);

        if (file.createNewFile())
            return "Arquivo criado!";
        else
            return "Arquivo já existe";
    }

    public static String remove(String name) {
        File file = new File(name);

        if(file.delete())
            return "Arquivo apagado";
        else
            return "Falha ao apagar aquivo";
    }
}