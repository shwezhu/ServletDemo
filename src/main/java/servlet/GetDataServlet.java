package servlet;
import database.Controller;
import database.DataEntity;
import database.Database;
import database.MysqlDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetDataServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(GetDataServlet.class);

    // @override: If programmer makes any mistake such as wrong method name,
    // wrong parameter types while overriding, you would get a compile time error.
    // As by using this annotation you instruct compiler that you are overriding this method.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Returns the value of a request parameter as a String,
        // or null if the parameter does not exist.
        String strDate = request.getParameter("start");
        String endDate = request.getParameter("end");

        if (strDate == null || endDate == null) {
            sendMessage("Please specify start and end date", response);
            return;
        }

        String sql;
        String urlPattern = request.getServletPath();
        //  the == operator returns false because str1 and str2 are different objects in memory, even though they have the same value.
        //  The .equals() method, returns true because it compares the contents of the objects.
        if (urlPattern.equals("/temperature") || urlPattern.equals("/humidity")) {
            sql = "select * from " + urlPattern.substring(1) + " where (date <= '" + endDate + "' AND date >= '" + strDate + "')";
        } else {
            sendMessage("the request format should like: localhost:8080/temperature?&start=date&end=date", response);
            return;
        }

        JsonArray results = getData(sql, response);
        if (results == null) return;
        sendData(results, response);
    }

    public JsonArray getData(String sql, HttpServletResponse response) throws IOException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        ArrayList<DataEntity> results = new ArrayList<>();
        Connection conn = getConnection();

        if (conn == null) {
            logger.error("this.conn == null, is true");
            sendMessage("We cannot connect our database now", response);
            return null;
        }

        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(new DataEntity(resultSet.getDouble(1), resultSet.getDate(2).toString()));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            sendMessage(e.getMessage(), response);
            return null;
        } finally {
            close(conn, statement, resultSet);
        }

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(results, new TypeToken<ArrayList<DataEntity>>(){}.getType());

        return jsonElement.getAsJsonArray();
    }

    public Connection getConnection() {
        Database database = new MysqlDatabase("jdbc:mysql://localhost:3306/greenhouse", "root", "778899");
        Controller controller = new Controller(database);
        Connection conn = null;
        try {
            conn = controller.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return conn;
    }

    private void close(Connection conn, PreparedStatement sta, ResultSet rs) {
        // 关闭顺序不能变
        try {
            if(rs !=null) rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        try {
            if(sta !=null) sta.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        try {
            if(conn !=null) conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendData(JsonArray results, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(results);
    }

    private void sendMessage(String message, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + message + "');");
        out.println("location='/index.jsp';");
        out.println("</script>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Hi Team Blue');");
        out.println("location='/index.jsp';");
        out.println("</script>");
    }
}
