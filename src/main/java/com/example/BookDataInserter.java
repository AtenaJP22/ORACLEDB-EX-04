package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BookDataInserter {
    private static final String DB_URL = "jdbc:oracle:thin:@//34.46.199.196:1521/XE";
    private static final String USER = "system";
    private static final String PASS = "YourSecurePassword123";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Create prepared statement
            String sql = "INSERT INTO BOOK (ID, NAME, ISBN, CREATE_DATE) VALUES (?, ?, ?, SYSDATE)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Generate and insert 100 records
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                // Generate random book name
                String bookName = "Book " + i + " - " + generateRandomTitle(random);
                
                // Generate random ISBN (format: XXX-X-XXXXX-XXX-X)
                String isbn = String.format("%03d-%d-%05d-%03d-%d",
                    random.nextInt(1000),
                    random.nextInt(10),
                    random.nextInt(100000),
                    random.nextInt(1000),
                    random.nextInt(10));

                // Set parameters
                pstmt.setInt(1, i); // ID
                pstmt.setString(2, bookName); // NAME
                pstmt.setString(3, isbn); // ISBN

                // Execute insert
                pstmt.executeUpdate();
                
                System.out.println("Inserted book: " + bookName + " (ISBN: " + isbn + ")");
            }
            
            System.out.println("Successfully inserted 100 records into BOOK table!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomTitle(Random random) {
        String[] adjectives = {"Mysterious", "Ancient", "Forgotten", "Hidden", "Lost", 
                              "Secret", "Dark", "Silent", "Final", "First"};
        String[] nouns = {"Journey", "Kingdom", "Legacy", "Theory", "Code", 
                         "World", "Secret", "Treasure", "Promise", "Vision"};
        
        return adjectives[random.nextInt(adjectives.length)] + " " + 
               nouns[random.nextInt(nouns.length)];
    }
}
