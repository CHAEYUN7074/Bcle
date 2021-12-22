package com.test.mvc;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IClubDAO
{
	public ClubDTO showPreOpenClub(String cid) throws SQLException; //
   
   public ArrayList<ClubDTO> preopenList() throws SQLException ; //
   
   public ArrayList<ClubDTO> searchList(String title) throws SQLException;
   
   public ArrayList<Region> regionLList() throws SQLException; //완료
   
   public ArrayList<Region> regionSList(String region_l_id) throws SQLException; //완료
   
   public ArrayList<Category> categoryLList() throws SQLException; //완료 
   
   public ArrayList<Category> categorySList(String category_l_id) throws SQLException; //완료
   
   public int insert(ClubDTO club) throws SQLException;// 

   public int checkTitle(String title) throws SQLException; //동아리명 중복검사

   public int remove(String cid) throws SQLException; //동아리 삭제

   public int modify(ClubDTO club) throws SQLException; //동아리 업데이트

   
   //정렬, update, 폐쇄 등 추가될 예정
}