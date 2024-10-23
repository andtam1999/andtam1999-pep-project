package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    // Returns true if an account with the given username exists, false otherwise
    public boolean checkUsernameExists(String username) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT 1 FROM account WHERE username = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Returns true if an account with the given account ID exists, false otherwise
    public boolean checkAccountIdExists(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT 1 FROM account WHERE account_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Account getAccountByUserPass(String username, String password) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertNewAccount(String username, String password) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES (?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), username, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}