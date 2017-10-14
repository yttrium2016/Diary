package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.DiaryDao;
import studio.yttrium.pojo.Diary;
import studio.yttrium.pojo.DiaryExample;
import studio.yttrium.service.DiaryService;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日记Service接口的实现类
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/16
 * Time: 23:23
 */
@Service
public class DiaryServiceImpl implements DiaryService {

    @Resource
    private DiaryDao diaryDao;

    /**
     * 保存或者修改日记
     * @param diary
     * @return
     */
    public int saveOrUpdate(Diary diary) {
        if (diary.getId() == null) {
            return diaryDao.insertSelectiveResultId(diary);
        } else {
            return diaryDao.updateByPrimaryKeySelective(diary);
        }
    }

    /**
     * 获得日记
     * @param id
     * @param userId
     * @return
     */
    public Diary getDiary(int id, Integer userId) {
        DiaryExample example = new DiaryExample();
        DiaryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if (userId != null){
            criteria.andCreateByEqualTo(userId);
        }
        List<Diary> list = diaryDao.selectByExampleWithBLOBs(example);
        if (list != null && list.size() == 1){
            return list.get(0);
        }else {
            return null;
        }

    }

    /**
     * 获得日记列表
     * @param diary
     * @return
     */
    public List<Diary> listDiary(Diary diary) {
        return diaryDao.selectLeftUser(diary);
    }

    /**
     * 获得朋友的日记
     * @param privilegeList
     * @param title
     * @return
     */
    public List<Diary> listDiaryByUserIds(List<Integer> privilegeList, String title) {
        DiaryExample example = new DiaryExample();
        DiaryExample.Criteria criteria = example.createCriteria();
        criteria.andCreateByIn(privilegeList);
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        example.setOrderByClause(" create_on DESC ");
        return diaryDao.selectByExampleLeftUser(example);
    }

    /**
     * 删除日记(软删除)
     * @param id
     * @param userId
     * @return
     */
    public int updateDiaryByDelete(int id, int userId) {
        Diary diary = new Diary();
        diary.setId(id);
        diary.setCreateBy(0-userId);
        return diaryDao.updateByPrimaryKeySelective(diary);
    }
}
