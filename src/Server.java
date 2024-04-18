import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import conectar.ConectarMySQL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

class MultiClientHandler extends Thread {
    private final Socket socket;
    private final DataInputStream dis;
    private final BlockingQueue<MANEJADORMENSAJES> messageQueue;//COLA DE MENSAJES ENCAPSULADOS
    public static ActiveHub ManejadorActivos = new ActiveHub();
    List<List<String>> Ubicaciones = new ArrayList<>();
    int busqueda=0;
    List<List<TankManager.TankElement>> tankList;
    String ubiX="";
    String ubiY= "";
    
    
    public MultiClientHandler(Socket socket, DataInputStream dis, BlockingQueue<MANEJADORMENSAJES> messageQueue,List<List<TankManager.TankElement>> tankList) {
        this.socket = socket;
        this.dis = dis;
        this.messageQueue = messageQueue;
        this.tankList = tankList;
    }
    

    public void run() {
        try {
            String received;
            
            while (true) {
                received = dis.readUTF();
                if (received.equals("Exit")) {
                    System.out.println("Client " + socket + " has terminated the connection.");
                    System.out.println("Closing the connection.");
                    String nombreCliente = ClienteManager.obtenerNombreCliente(socket);
                    //System.out.println(nombreCliente);
                    ManejadorActivos.PermutaEdoUser(0,nombreCliente);
                    socket.close();
                    System.out.println("Connection closed.");
                    break;
                }
                
                //System.out.println("Message received from client: " + received);
              
                String nombreCliente = ClienteManager.obtenerNombreCliente(socket);
                System.out.println(nombreCliente);
                messageQueue.add(new MANEJADORMENSAJES(received, socket, nombreCliente));//AGREGA EL MENSAJE A LA COLA
                
                //DA CLICK A JUGAR
                if(received.equals("JUG@RYA")){
                
                    
                    System.out.println("Lista de tanques antes de buscar:");
                    imprimirTankList(tankList);
                    
                    buscaubicaciontank(tankList);
                    
                    System.out.println("Lista de tanques despues de buscar:");
                    imprimirTankList(tankList);
                    
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    
                    dos.writeUTF(ubiX);
                    dos.writeUTF(ubiY);
                    dos.writeUTF("USUARIO: " + nombreCliente);

                }
                
            }
        } catch (IOException e) {
            System.out.println("Error reading from client: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(MultiClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream: " + e.getMessage());
            }
        }
    }
    
    public void buscaubicaciontank(List<List<TankManager.TankElement>> tankList){
    
        for (List<TankManager.TankElement> ubicacion : tankList) {
            for (TankManager.TankElement elemento : ubicacion) {
                if (!elemento.isOcupado() && busqueda!=1) {
                    
                    ubiX = elemento.getUbicacionX();
                    ubiY = elemento.getUbicacionY();
                    elemento.setOcupado(true);
                    busqueda=1;
                }
            }
        }
    }
    
    
    //ESTE METODO ES PRINCIPALMENTE UN DEBUG XDD
    public static void imprimirTankList(List<List<TankManager.TankElement>> tankList) {
        for (List<TankManager.TankElement> ubicacion : tankList) {
            for (TankManager.TankElement elemento : ubicacion) {
                System.out.println("Ubicación X: " + elemento.getUbicacionX() + ", Ubicación Y: " + elemento.getUbicacionY() + ", Ocupado: " + elemento.isOcupado());
            }
        }
    }
    
   
     }



class ClienteManager {
    private static Map<Socket, ClientData> clientStreams;

    // Método para establecer el Map clientStreams
    public static void setClientStreams(Map<Socket, ClientData> streams) {
        clientStreams = streams;
    }

    // Método para obtener el nombre del cliente asociado con un socket
    public static String obtenerNombreCliente(Socket socket) {
        if (clientStreams != null && clientStreams.containsKey(socket)) {
            return clientStreams.get(socket).getUser();
        }
        return null; // Si no se encuentra el nombre del cliente, retorna null o maneja la situación según tu necesidad
    }
}



class ClientData{
private DataOutputStream outputStream;
private String user;

public ClientData(DataOutputStream outputStream, String user){

    this.outputStream = outputStream;
    this.user = user;
}

public DataOutputStream getOutputStream(){

    return outputStream;
}

public String getUser(){

    return user;
}

}

class MANEJADORMENSAJES {
    private final String message;
    private final Socket sender;
    private final String user;

    public MANEJADORMENSAJES(String message, Socket sender, String user) {//INCLUYE EL MENSAJE Y EL SOCKET QUE LO ENVIO
        this.message = message;
        this.sender = sender;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public Socket getSender() {
        return sender;
    }
    
    public String getUser() {
        return user;
    }
}


class ActiveHub {


    public ActiveHub() {//INCLUYE EL MENSAJE Y EL SOCKET QUE LO ENVIO

    }
    
    public static void ReiniciaEdoUser () throws SQLException{
            
            String queryupdateactive = "UPDATE USERS SET active=0";

            ConectarMySQL conecta = new ConectarMySQL();
            Connection connection = conecta.getConnection();
            
           Statement myQuery1 = (Statement) connection.createStatement ();
           boolean check = myQuery1.execute(queryupdateactive);
           
           //System.out.println("DATA: \n" + data);
    }
    
    public void PermutaEdoUser (int activa, String user) throws SQLException{
            
            String queryupdateactive = "";
            if(activa==0)
            {
            queryupdateactive = "UPDATE USERS SET active=0 WHERE user= " + "'" + user + "'";
            //System.out.println("ENTRO Y ESTA ES LA CONSULTA: " + queryupdateactive);
            }else{
                if(activa ==1){
                   queryupdateactive = "UPDATE USERS SET active=1 WHERE user= " + "'" + user + "'";
                }
            
            }
            
            ConectarMySQL conecta = new ConectarMySQL();
            Connection connection = conecta.getConnection();
            
           
           //String data = "";//APROACH ANTERIOR
           
           Statement myQuery1 = (Statement) connection.createStatement ();
           boolean check = myQuery1.execute(queryupdateactive);
           
           //System.out.println("DATA: \n" + data);

           
    }


}


class Server {
    public static String clienteNombre="";
    public static ActiveHub ManejadorActivos = new ActiveHub();


    public static void main(String[] args) throws SQLException {
                
        TankManager tank = new TankManager();
       
        List<List<TankManager.TankElement>> tankList = tank.getTankList();
        
        BlockingQueue<MANEJADORMENSAJES> messageQueue = new LinkedBlockingQueue<>();//LISTA MENSAJES
        
        Map<Socket, ClientData> clientStreams = new HashMap<>();//LISTA DE CLIENTES, SE ASOCIA CADA SOCKET A SU FLUJO DE SALIDA
        ClienteManager.setClientStreams(clientStreams);
        
        //INSTANCIA CLASE QUE MANEJA LOS USUARIOS ACTIVOS
        ManejadorActivos.ReiniciaEdoUser();//PON TODOS EN INACTIVOS AL INICIO
        
        
        
        try {
            ServerSocket serverSocket = new ServerSocket(2555);//SE CREA EL SOCKET EN DONDE VA A ESCUCHAR
            System.out.println("ServerSocket: " + serverSocket);

            Thread senderThread = new Thread(() -> {//HILO DE REENVIO
                try {
                    while (true) {
                        MANEJADORMENSAJES mensajeencapsulado = messageQueue.take();//TOMA EL MENSAJE DE LA COLA Y LO GUARDA EN EL TIPO MESSAGEWRAPPER
                        String message = mensajeencapsulado.getMessage();//DESPUES ESTE LEE DEL METODO GETMESSAGE Y LO PONE EN UNA CADENA
                        String user = mensajeencapsulado.getUser();
                        System.out.println("Message received from client: " + message);
                        
                        //REENVIA LA INFO A TODOS LOS CLIENTES EXCEPTO AL QUE ENVIA

                        for (Map.Entry<Socket, ClientData> entry : clientStreams.entrySet()) {//EL ENTRYSET PERMITE ITERAR ENTRE CADA ELEMENTO O CADA MAPA DEL HASH
                            
                            Socket clientSocket = entry.getKey();//OBTIENE LA CLAVE QUE ES EL SOCKET 
                            
                            //DataOutputStream clientStream = entry.getValue();//AHORA OBTIENE TAL CUAL EL VALOR DE LA LLAVE, QUE SERIA EL DOS A REENVIAR
                            
                            ClientData clientData = entry.getValue();//INSTANCIA LA CLASE DE LA INFO DEL CLIENTE
                            DataOutputStream clientStream = clientData.getOutputStream();//AHORA PONLA EN EL TIPO CLIENTSTREAM
                            
                            String nombreCliente = clientData.getUser();
                            
                            if (!clientSocket.equals(mensajeencapsulado.getSender())) { //SI EL CLIENTE ES DIFERENTE A QUIEN LO MANDÓ, ENTONCES REENVIALO A ESE CLIENTE
                                try {
                                    clientStream.writeUTF(user + ": " + message);//ENVIA EL MENSAJE AL CLIENTE ESPECIFICO
                                    clientStream.flush();
                                    System.out.println("Sending message to client: " + clientSocket);
                                } catch (IOException e) {
                                    System.out.println("Error sending message to client: " + e);
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
            });
            senderThread.start();//EMPIEZA EL HILO

            while (true) {//HILO DE CONTACTO CON EL CLIENTE
                //CONEXION BASE DE DATOS
                ConectarMySQL conecta = new ConectarMySQL();
                Connection connection = conecta.getConnection();
                //CONEXION SOCKETS CLIENTE
                Socket socket = serverSocket.accept();
                System.out.println("A new Client is connected: " + socket);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                
                //Variable de cadena
                String received;
                String tipo;
                String recibidoconfirmacion;
                clienteNombre="";
                dos.writeUTF("escribe usuario");
                
                received = dis.readUTF();
                
                tipo="us";
                
                recibidoconfirmacion=bd(received,tipo);
                
                //System.out.println(recibidoconfirmacion);
                                
                if(recibidoconfirmacion.equals("fracaso")){
                    dos.writeUTF("USUARIO INCORRECTO, ADIOS POPÓ");
                    System.out.println("Client " + socket + " has terminated the connection.");
                    System.out.println("Closing the connection.");
                    socket.close();
                    System.out.println("Connection closed.");
                    dis.close();
                    dos.close();
                    clienteNombre="";

                    continue;
                }
                else{
                    if(recibidoconfirmacion.equals("usuario_ocupado")){
                    dos.writeUTF("USUARIO OCUPADO, ELIGE OTRO");
                    System.out.println("Client " + socket + " has terminated the connection.");
                    System.out.println("Closing the connection.");
                    socket.close();
                    System.out.println("Connection closed.");
                    dis.close();
                    dos.close();
                    clienteNombre="";

                    continue;
                    }else{
                    dos.writeUTF(recibidoconfirmacion);
                //clienteNombre= recibidoconfirmacion;
                    }
                }
                
                dos.writeUTF("escribe contraseña");
                received = dis.readUTF();
                tipo="pass";
                recibidoconfirmacion=bd(received,tipo);
                //System.out.println(recibidoconfirmacion);
                if(recibidoconfirmacion.equals("fracaso")){
                    dos.writeUTF("CONTRASEÑA INCORRECTA, ADIOS POPÓ");
                    System.out.println("Client " + socket + " has terminated the connection.");
                    System.out.println("Closing the connection.");
                    socket.close();
                    System.out.println("Connection closed.");
                    dis.close();
                    dos.close();
                    clienteNombre="";
                    continue;
                }
                else{
                ManejadorActivos.PermutaEdoUser(1,clienteNombre);
                dos.writeUTF(recibidoconfirmacion);
                }
                
               
                System.out.println(clienteNombre + ": ACTIVO");
 
                //CUANDO ENTRA...
                System.out.println("Assigning new Thread for this Client.");
                clientStreams.put(socket, new ClientData(dos, clienteNombre));
                dos.writeUTF("VAS");//ENVIA EL PRIMER MENSAJE PARA INICIAR LA COMUNICACION
                
                //HILO DE LA RECEPCION DE MENSAJES DE LOS CLIENTES
                Thread t = new MultiClientHandler(socket, dis, messageQueue,tankList);
                t.start();//INICIA EL HILO
            }
        } catch (IOException e) {
            System.out.println("Server Error: " + e);
        }
    }
    
    
    public static String bd (String received,String tipo){
        
       ConectarMySQL conecta = new ConectarMySQL();
       Connection connection = conecta.getConnection();
       
        //System.out.println("ENTRO");
       try{
           
           String query;
           String queryuseractive=""; // VARIABLE QUE DEFINE EL QUERY DEL USER QUE INTENTA INICIAR
           boolean en_uso = false; // VARIABLE QUE DICE SI EL USER ESTÁ O NO EN USO
           
           if(tipo.equals("us"))
           {
             query = "SELECT user FROM USERS";

             //System.out.println("ENTRO");
             
           }
           else{
              if(tipo.equals("pass")){
              query = "SELECT pass FROM USERS";
           }else{
                   //System.out.println("ENTRO");

                   return "fracaso";

                   
                   }

           }

           
           Statement myQuery = (Statement) connection.createStatement ();
           ResultSet rs = myQuery.executeQuery(query);
           
           //String data = "";//APROACH ANTERIOR
           List<String> userData = new ArrayList<>();//ARRAY QUE ALMACENA RESULTADO DE CONSULTO SQL
           
           while(rs.next()){
               for(int i = 1; i <= 1; i++){//ESTO ES NECESARIO AJUSTARLO A LA CANTIDAD DE CAMPOS DE LA TABLA
                   //data += rs.getString(i);   
                   userData.add(rs.getString(i));
               }
           }
           
           //System.out.println("DATA: \n" + data);
           //System.out.println("DATA: \n" + userData);

           if(userData.contains(received) && tipo.equals("us"))
           {
               //System.out.println("ENTRO");
               clienteNombre=received;
               queryuseractive = " SELECT active FROM USERS WHERE user = " + "'" + clienteNombre + "'";
               //System.out.println(queryuseractive);

               en_uso = verificaactive(queryuseractive);
               
               if(en_uso == true)
               {
                 return "usuario_ocupado";
               }
               

               return "exito";
           }
           else{
               if(userData.contains(received) && tipo.equals("pass")){
                    //System.out.println("ENTRO");//MENSAJE DEBUG
                    //clienteNombre=received;
                    return "exito";
               }else{
               return "fracaso";
               }
           }
       }catch(SQLException e){
         System.out.println(e);

       }

        return "fracaso";
    }
    
    
    public static boolean verificaactive (String queryactive) throws SQLException{
            
            ConectarMySQL conecta = new ConectarMySQL();
            Connection connection = conecta.getConnection();
            
            Statement myQuery = (Statement) connection.createStatement ();
           ResultSet rs = myQuery.executeQuery(queryactive);
           
           //String data = "";//APROACH ANTERIOR
           String active="";//ARRAY QUE ALMACENA RESULTADO DE CONSULTO SQL
           
           while(rs.next()){
               for(int i = 1; i <= 1; i++){//ESTO ES NECESARIO AJUSTARLO A LA CANTIDAD DE CAMPOS DE LA TABLA
                   //data += rs.getString(i);   
                   active += rs.getString(i); 
               }
           }
           
           //System.out.println("DATA: \n" + data);
           
           if(active.equals("0")){
               return false;
           }
           else{
               return true;
           }
           
        }
        
    
    public static void ReiniciaEdoUser () throws SQLException{
            
            String queryupdateactive = "UPDATE USERS SET active=0";

            ConectarMySQL conecta = new ConectarMySQL();
            Connection connection = conecta.getConnection();
            
           Statement myQuery1 = (Statement) connection.createStatement ();
           boolean check = myQuery1.execute(queryupdateactive);
           
           //System.out.println("DATA: \n" + data);
        }
    
}