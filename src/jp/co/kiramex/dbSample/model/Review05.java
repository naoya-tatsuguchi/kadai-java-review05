package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidh?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "naoya0525"
                );

            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            
            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("検索idを入力してください > ");
            int input = keyNum(); 
            pstmt.setInt(1,input);
            rs = pstmt.executeQuery();
                   

            // 7. 結果を表示する
            while (rs.next()) {
            int Id = rs.getInt("id");
            String Name = rs.getString("name");
            int Age = rs.getInt("age");
            
            System.out.println(Name);
            System.out.println(Age);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
    }finally {
        // 8. 接続を閉じる
        if( rs != null ){
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }
        if( pstmt != null ){
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Statementを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }
        if( con != null ){
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("データベース切断時にエラーが発生しました。");
                e.printStackTrace();
            }
        }
    }
    }
   
    private static int keyNum() {
        String line = null;
        int result = 0;
        try {
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
        line = key.readLine();
            result = Integer.parseInt(line);
        } catch (NumberFormatException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

