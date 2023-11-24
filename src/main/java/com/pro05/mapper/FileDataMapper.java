package com.pro05.mapper;

import com.pro05.entity.FileData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileDataMapper {
    @Select("SELECT * FROM fileData WHERE tableName=#{tableName} AND columnNo=#{columnNo}")
    public List<FileData> fileDataBoardList(String tableName, Long columnNo);

    @Select("SELECT * FROM fileData WHERE fileNo=#{fileNo}")
    public FileData fileDataGet(Long fileNo);

    @Insert("INSERT INTO fileData (tableName, columnNo, originName, saveName, savePath, fileType, status) VALUES (#{tableName}, #{columnNo}, #{originName}, #{saveName}, #{savePath}, #{fileType}, #{status})")
    public int fileDataInsert(FileData fileData);
    @Select("SELECT * FROM fileData ORDER BY fileNo DESC LIMIT 1")
    public FileData fileDataGetLast();

    @Update("UPDATE fileData SET tableName=#{tableName}, columnNo=#{columnNo} WHERE fileNo=#{fileNo}")
    public int fileDataUpdate(int fileData);

    @Update("UPDATE fileData SET authority=#{authority} WHERE fileNo=#{fileNo}")
    public int fileDataAuthorityUpdate(String authority, Long fileNo);
    @Update("UPDATE fileData SET authority='REMOVE' WHERE fileNo=#{fileNo}")
    public int fileDataRemoveUpdate(Long fileNo);
    @Update("UPDATE fileData SET authority='REMOVE' WHERE columnNo=#{columnNo}")
    public int fileDataBoardRemoveUpdate(Long columnNo);
    @Delete("DELETE FROM fileData WHERE fileNo=#{fileNo}")
    public int fileDataDelete(Long fileNo);

}
