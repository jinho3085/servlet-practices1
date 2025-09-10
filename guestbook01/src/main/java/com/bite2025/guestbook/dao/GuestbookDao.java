package com.bite2025.guestbook.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bite2025.guestbook.vo.GuestbookVo;

public class GuestbookDao {

    
    public List<GuestbookVo> findAll() {
        List<GuestbookVo> list = new ArrayList<>();
        String sql = "SELECT id, name, password, message, reg_date FROM guestbook ORDER BY id DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                GuestbookVo vo = new GuestbookVo();
                vo.setId(rs.getLong("id"));
                vo.setName(rs.getString("name"));
                vo.setPassword(rs.getString("password"));
                vo.setMessage(rs.getString("message"));
                vo.setRegDate(rs.getString("reg_date"));
                list.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(GuestbookVo vo) {
        String sql = "INSERT INTO guestbook(name, password, message, reg_date) VALUES (?, ?, ?, NOW())";
        int result = 0;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getPassword());
            pstmt.setString(3, vo.getMessage());
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    public boolean delete(Long id, String password) {
        String sql = "DELETE FROM guestbook WHERE id=? AND password=?";
        int result = 0;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.setString(2, password);
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }
    
    private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url  = "jdbc:mariadb://192.168.0.178:3306/webdb";
			con =  DriverManager.getConnection (url, "webdb", "webdb");
		} catch(ClassNotFoundException ex) {
			System.out.println("Driver Class Not Found");
		}
		
		return con;		
	}
}
