package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {
    public Message insertNewMessage(int account_id, String message_text, long time_posted_epoch) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            pstmt.setString(2, message_text);
            pstmt.setLong(3, time_posted_epoch);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return new Message(rs.getInt("message_id"), account_id, message_text, time_posted_epoch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"), 
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                msgList.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgList;
    }

    public Message getMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, message_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Message msg = new Message(message_id,
                                        rs.getInt("posted_by"), 
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, message_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Message msg = new Message(message_id,
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesPostedByAccountId(int account_id) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"),
                                        account_id,
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                msgList.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgList;
    }
}
