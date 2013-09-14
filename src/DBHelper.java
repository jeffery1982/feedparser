import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBHelper {
	
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;

    private static String driver = "";
    private static String url = "";
    private static String userName = "";
    private static String password = "";

    private static Properties pp = null;
    private static FileInputStream fis = null;

    public static Connection getConn() {
        return conn;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

    public static ResultSet getRs() {
        return rs;
    }
   
    public static CallableStatement getCs() {
        return cs;
    }

    
    static {
        try {
            // Read from dbinfo.properties
            pp = new Properties();
            fis = new FileInputStream("dbinfo.properties");
            pp.load(fis);
            driver = pp.getProperty("driver");
            url = pp.getProperty("url");
            userName = pp.getProperty("userName");
            password = pp.getProperty("password");

            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            fis = null;

        }
    }

    /**
     * Get connection
     * @return
     */
    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Execute sql statements
     * @param sql
     * @param parameters
     */
    public static void executeUpdateMultiParams(String[] sql,
            String[][] parameters) {
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            for (int i = 0; i < sql.length; i++) {
                if (parameters[i] != null) {
                    ps = conn.prepareStatement(sql[i]);
                    for (int j = 0; j < parameters[i].length; j++)
                        ps.setString(j + 1, parameters[i][j]);
                }
                ps.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            // 关闭资源
            close(rs, ps, conn);
        }
    }

    /**
     * update/delete/insert
     * sql format:UPDATE tablename SET columnn = ? WHERE column = ?
     * @param sql
     * @param parameters
     */
    public static void executeUpdate(String sql, String[] parameters) {
        try {
            // 1.创建一个ps
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            // 给？赋值
            if (parameters != null)
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            // 执行
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();// 开发阶段
            throw new RuntimeException(e.getMessage());
        } finally {
            // 关闭资源
            close(rs, ps, conn);
        }
    }

    /**
     * Select
     * @param sql
     * @param parameters
     * @return
     */
    public static ResultSet executeQuery(String sql, String[] parameters) {
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {

        }
        return rs;
    }

    /**
     * Call sp
     * format: call procedureName(parameters list)
     * @param sql
     * @param parameters
     */
    public static void callProc(String sql, String[] parameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);
            // 给？赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++)
                    cs.setObject(i + 1, parameters[i]);
            }
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            // 关闭资源
            close(rs, cs, conn);
        }
    }

    /**
     * Call sp with "in parameters"
     * @param sql
     * @param inparameters
     * @return
     */
    public static CallableStatement callProcInput(String sql, String[] inparameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);
            if(inparameters!=null)
                for(int i=0;i<inparameters.length;i++)
                    cs.setObject(i+1, inparameters[i]);               
            cs.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally{
           
        }
        return cs;
    }
   
    /**
     * Call sp with "out parameters"
     * @param sql
     * @param outparameters
     * @return
     */
    public static CallableStatement callProcOutput(String sql,Integer[] outparameters) {
        try {
            conn = getConnection();
            cs = conn.prepareCall(sql);                   
            //给out参数赋值
            if(outparameters!=null)
                for(int i=0;i<outparameters.length;i++)
                    cs.registerOutParameter(i+1, outparameters[i]);
            cs.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally{
           
        }
        return cs;
    }

    /**
     * Close resultset
     * @param rs
     * @param ps
     * @param conn
     */
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        rs = null;
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        ps = null;
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        conn = null;
    }
}
