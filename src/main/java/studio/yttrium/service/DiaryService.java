package studio.yttrium.service;

import studio.yttrium.pojo.Diary;

import java.util.List;

/**
 * 日记Service接口
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/16
 * Time: 23:22
 */
public interface DiaryService {
    /**
     * 保存或者修改日记
     *
     * @param diary
     * @return
     */
    int saveOrUpdate(Diary diary);

    /**
     * 通过id查找日记
     *
     * @param id
     * @return
     */
    Diary getDiary(int id, Integer userId);

    /**
     * 查询所有日记
     *
     * @param diary
     * @return
     */
    List<Diary> listDiary(Diary diary);

    /**
     * 查询所有日记通过userIds和name
     *
     * @param privilegeList
     * @param title
     * @return
     */
    List<Diary> listDiaryByUserIds(List<Integer> privilegeList, String title);

    /**
     * 删除
     *
     *
     * @param id
     * @param userId
     * @return
     */
    int updateDiaryByDelete(int id, int userId);
}
