package com.test.mvc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class ClubDAO implements IClubDAO
{
   private DataSource dataSource;

   public void setDataSource(DataSource dataSource)
   {
      this.dataSource = dataSource;
   }
   
   @Override
   // 개설 예정 동아리 클릭시(ClubBeforeOpenedContents.jsp)에 보여질 항목들 
   public ClubDTO showPreOpenClub(String cid) throws SQLException 
   {
      ClubDTO result = new ClubDTO();
      Connection conn = dataSource.getConnection();
      String sql = "SELECT TITLE,CONTENT,NICKNAME,PREOPENDATE"
            + ",L_CAT,S_CAT,CITY,LOCAL"
            + ",MAX,LIMIT_ID,AGELIMIT_ID,MIN_AGE,MAX_AGE,DAY "
            + "FROM CLUB_VIEW "
            + "WHERE CID = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, cid);
      ResultSet rs = pstmt.executeQuery();
      
      while(rs.next())
      {
         result.setTitle(rs.getString("TITLE"));
         result.setContent(rs.getString("CONTENT"));
         result.setNickname(rs.getString("NICKNAME"));
         result.setPreopendate(rs.getString("PREOPENDATE"));
         result.setL_cat(rs.getString("L_CAT"));
         result.setS_cat(rs.getString("S_CAT"));
         result.setCity(rs.getString("CITY"));
         result.setLocal(rs.getString("LOCAL"));
         result.setMax(Integer.parseInt(rs.getString("MAX")));
         result.setLimit_id(rs.getString("LIMIT_ID"));
         result.setAgelimit_id(rs.getString("AGELIMIT_ID"));
         result.setMin_age(Integer.parseInt(rs.getString("MIN_AGE")));
         result.setMax_age(Integer.parseInt(rs.getString("MAX_AGE")));
         result.setDay(Integer.parseInt(rs.getString("DAY")));

      }
      
      rs.close();
      pstmt.close();
      conn.close();
      return result;
   
   }
   
    // 개설 예정 동아리 리스트 (ClubBeforeOpenedList.jsp)   --추가
   
   public ArrayList<ClubDTO> preopenList() throws SQLException 
   {
      ArrayList<ClubDTO> result = new ArrayList<ClubDTO>();
      Connection conn = dataSource.getConnection();
      String sql = "SELECT TITLE,CID,DAY,URL "
            + "FROM CLUB_VIEW"
            + " WHERE BDATE IS NULL";
      
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      
      while(rs.next())
      {
         ClubDTO dto = new ClubDTO();
               
          dto.setTitle(rs.getString("TITLE"));
          dto.setCid(rs.getString("CID"));
          dto.setDay(Integer.parseInt(rs.getString("DAY")));
          dto.setUrl(rs.getString("URL"));
          

          result.add(dto);
      }
         rs.close();
         pstmt.close();
         conn.close();
         
         return result;
      }
   
      // 개설 예정 동아리 리스트 검색 -- 추가
      public ArrayList<ClubDTO> searchList(String title) throws SQLException
      {
         ArrayList<ClubDTO> result = new ArrayList<ClubDTO>();
            Connection conn = dataSource.getConnection();
            String sql = "SELECT TITLE,CID,DAY "
                  + "FROM CLUB_VIEW"
                  + " WHERE TITLE LIKE '%"+title+"%'" ; 
         
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, title); //추가
         ResultSet rs = pstmt.executeQuery();
         

         while (rs.next())
         {
            ClubDTO dto = new ClubDTO();

            dto.setTitle(rs.getString("TITLE"));
            dto.setCid(rs.getString("CID"));
            dto.setDay(Integer.parseInt(rs.getString("DAY")));

            result.add(dto);
         }
         rs.close();
         pstmt.close();
         conn.close();

         return result;
      }
    // 지역 리스트 출력(시) ---city
      @Override
      public ArrayList<Region> regionLList() throws SQLException
      {
         ArrayList<Region> result = new ArrayList<Region>();
         
         Connection conn = dataSource.getConnection();
         
         String sql = "SELECT REGION_L_ID, CITY FROM REGION_L";

         PreparedStatement pstmt =  conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next())
         {
            Region dto = new Region();
            
            dto.setRegion_l_id(rs.getString("REGION_L_ID"));
            dto.setCity(rs.getString("CITY"));
            
            result.add(dto);
         }
         
         rs.close();
         pstmt.close();
         conn.close();
         
         return result;
      }
      
    // 군/구 
   @Override
   public ArrayList<Region> regionSList(String region_l_id) throws SQLException
   {
       ArrayList<Region> result = new ArrayList<Region>();
         
         Connection conn = dataSource.getConnection();
         
         String sql = "SELECT REGION_S_ID,REGION_L_ID,LOCAL FROM REGION_S WHERE REGION_L_ID=?";

         PreparedStatement pstmt =  conn.prepareStatement(sql);
         pstmt.setString(1, region_l_id);
         ResultSet rs = pstmt.executeQuery();

         
         while(rs.next())
         {
            Region dto = new Region();
            
            dto.setRegion_l_id(rs.getString("REGION_L_ID"));
           dto.setRegion_s_id(rs.getString("REGION_S_ID"));
           dto.setLocal(rs.getString("LOCAL")); 
            
            result.add(dto);
         }
         
         rs.close();
         pstmt.close();
         conn.close();
         
         return result;

   }

   @Override
   public ArrayList<Category> categoryLList() throws SQLException
   {
      ArrayList<Category> result = new ArrayList<Category>();
         
         Connection conn = dataSource.getConnection();
         
         String sql = "SELECT CATEGORY_L_ID,L_CAT FROM CATEGORY_L";

         PreparedStatement pstmt =  conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next())
         {
            Category dto = new Category();
            
            dto.setCategory_l_id(rs.getString("CATEGORY_L_ID"));
            dto.setL_cat(rs.getString("L_CAT"));
          
            
            result.add(dto);
         }
         
         rs.close();
         pstmt.close();
         conn.close();
         
         return result;

   }

   @Override
   public ArrayList<Category> categorySList(String category_l_id) throws SQLException
   {
       ArrayList<Category> result = new ArrayList<Category>();
         
         Connection conn = dataSource.getConnection();
         
         String sql = "SELECT CATEGORY_L_ID, CATEGORY_S_ID, S_CAT"
               + " FROM CATEGORY_S WHERE CATEGORY_L_ID = ?";

         PreparedStatement pstmt =  conn.prepareStatement(sql);
         pstmt.setString(1, category_l_id);
         ResultSet rs = pstmt.executeQuery();
         
         while(rs.next())
         {
            Category dto = new Category();
            
            dto.setCategory_s_id(rs.getString("CATEGORY_S_ID"));
            dto.setCategory_l_id(rs.getString("CATEGORY_L_ID"));
            dto.setS_cat(rs.getString("S_CAT"));
            
            result.add(dto);
         }
         
         rs.close();
         pstmt.close();
         conn.close();
         
         return result;

   }
   
   // 동아리 insert 메소드 
   @Override
   public int insert(ClubDTO club) throws SQLException
   {
      int result = 0;
      Connection conn = dataSource.getConnection();
      
      String sql = "{ call PRC_CLUB_INSERT(?,?,?,?,?,?,?,?,?)}";
      CallableStatement cstmt = conn.prepareCall(sql);
      cstmt.setString(1, club.getTitle());
      cstmt.setString(2, club.getId());
      cstmt.setInt(3, club.getMax());
      cstmt.setString(4, club.getContent());
      cstmt.setString(5, club.getCategory_s_id());
      cstmt.setString(6, club.getRegion_s_id());
      cstmt.setString(7, club.getUrl());
      cstmt.setString(8, club.getLimit_id());
      cstmt.setString(9, club.getAgelimit_id());
      
   
      result = cstmt.executeUpdate();
         
       cstmt.close();
       conn.close();
         
       return result;
   }

   
   
}