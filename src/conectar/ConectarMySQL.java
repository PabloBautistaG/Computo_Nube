
package conectar;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author ejr
 */

public class ConectarMySQL {
    public     static     final    String   URL   =
    "jdbc:mysql: //localhost:3306/restordb";
    public static final String USER = "root";
    public static final String PSWD = "Coc@C@la1234";
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection)DriverManager.getConnection(URL,USER,PSWD);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

}