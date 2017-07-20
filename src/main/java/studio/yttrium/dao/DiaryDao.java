package studio.yttrium.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import studio.yttrium.pojo.Diary;
import studio.yttrium.pojo.DiaryExample;

import java.util.List;

@Repository
public interface DiaryDao {
    long countByExample(DiaryExample example);

    int deleteByExample(DiaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Diary record);

    int insertSelective(Diary record);

    int insertSelectiveResultId(Diary record);

    List<Diary> selectByExampleWithBLOBs(DiaryExample example);

    List<Diary> selectByExample(DiaryExample example);

    List<Diary> selectByExampleLeftUser(DiaryExample example);

    List<Diary> selectLeftUser(Diary record);

    Diary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Diary record, @Param("example") DiaryExample example);

    int updateByExampleWithBLOBs(@Param("record") Diary record, @Param("example") DiaryExample example);

    int updateByExample(@Param("record") Diary record, @Param("example") DiaryExample example);

    int updateByPrimaryKeySelective(Diary record);

    int updateByPrimaryKeyWithBLOBs(Diary record);

    int updateByPrimaryKey(Diary record);

    int updateByDelete(int id);
}