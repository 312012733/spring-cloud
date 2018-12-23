package com.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.Student;
import com.bean.StudentIdCard;
import com.bean.Teacher;
import com.dao.IStudentAndTeacherDao;
import com.dao.IStudentDao;
import com.dao.IStudentIdCardDao;
import com.dao.ITeacherDao;
import com.service.IStudentService;
//import com.vo.Page;
import com.vo.StudentDTO;

@Service
public class StudentServiceImpl implements IStudentService
{
    
    @Autowired
    private IStudentDao stuDao;
    
    @Autowired
    private ITeacherDao teacherDao;
    
    @Autowired
    private IStudentAndTeacherDao stuAndteacherDao;
    
    @Autowired
    private IStudentIdCardDao idCardDao;
    
    @Transactional
    @Override
    public void addStudent(StudentDTO stuDTO)
    {
        Student stu = new Student();
        
        stu.setId(UUID.randomUUID().toString());
        stu.setCreateTime(System.currentTimeMillis());
        stu.setLastModifyTime(stu.getCreateTime());
        
        stu.setAge(stuDTO.getAge());
        stu.setName(stuDTO.getName());
        stu.setGender(stuDTO.getGender());
        stu.setMyClass(stuDTO.getMyClass());
        
        // 处理老师的关系
        addStuAndTeacher(stuDTO.getTeacherIds(), stu.getId(), teacherDao, stuAndteacherDao);
        
        stuDao.addStudent(stu);
    }
    
    @Override
    public StudentDTO findStudentById(String stuId)
    {
        
        Student stu = stuDao.findStudentById(stuId);
        
        if (stu == null)
        {
            return null;
        }
        
        List<Teacher> unOwerTeachers = teacherDao.findTeachersByExcludStuIds(stuId);
        
        StudentDTO stuDTO = new StudentDTO();
        
        stuDTO.setAge(stu.getAge());
        stuDTO.setName(stu.getName());
        stuDTO.setGender(stu.getGender());
        stuDTO.setMyClass(stu.getMyClass());
        
        stuDTO.setOwerTeachers(stu.getTeachers());
        stuDTO.setUnOwerTeachers(unOwerTeachers);
        
        return stuDTO;
    }
    
    @Transactional
    @Override
    public void updateStudent(StudentDTO stuDTO)
    {
        String stuId = stuDTO.getId();
        
        Student dbStu = stuDao.findStudentById(stuId);
        
        if (null == dbStu)
        {
            throw new SecurityException("update stu error. stuId is not found. stuId:" + stuId);
        }
        
        dbStu.setAge(stuDTO.getAge());
        dbStu.setName(stuDTO.getName());
        dbStu.setLastModifyTime(System.currentTimeMillis());
        dbStu.setMyClass(stuDTO.getMyClass());
        
        // TODO处理老师的关系
        // 先清除 在添加
        stuAndteacherDao.deleteByStudentId(dbStu.getId());
        this.addStuAndTeacher(stuDTO.getTeacherIds(), stuId, teacherDao, stuAndteacherDao);
        
        stuDao.updateStudent(dbStu);
    }
    
    @Transactional
    @Override
    public void delStudent(String stuId)
    {
        delStu(stuId);
    }
    
    @Transactional
    @Override
    public void batchDelStudents(String[] ids)
    {
        
        if (null == ids || ids.length <= 0)
        {
            throw new SecurityException("batch del stu error. stuId is null.");
        }
        
        // IStudentDao stuDao =
        // MybatisUtils.getCurrentSession().getMapper(IStudentDao.class);
        
        for (String stuId : ids)
        {
            delStu(stuId);
        }
        
        // stuDao.batchDelStudent(ids);
    }
    
    @Override
    public Page<Student> findStudentsByPage(Pageable pageable, Student condition)
    {
        List<Student> stuList = stuDao.findStudentsByPage(pageable, condition);
        Long totalCount = stuDao.queryCount(condition);
        
        PageImpl<Student> pageImpl = new PageImpl<>(stuList, pageable, totalCount);
        
        return pageImpl;
    }
    
    @Override
    public void bindStuIdcard(String stuId)
    {
        Student stu = stuDao.findStudentById(stuId);
        
        // 验证
        
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        if (null != stu.getStudentIdCard())
        {
            
            throw new SecurityException("idcard is exist. ");
        }
        
        // 生成一个的学生证
        StudentIdCard studentIdCard = buildStuIdCard();
        idCardDao.save(studentIdCard);
        
        // 设置学生和学生证关系
        stu.setStudentIdCard(studentIdCard);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        stuDao.updateStudent(stu);
    }
    
    @Override
    public void unBindStuIdcard(String stuId)
    {
        Student stu = stuDao.findStudentById(stuId);
        
        // 验证
        
        if (null == stu)
        {
            throw new SecurityException("stu is not found. id is " + stuId);
        }
        
        String idCardId = null;
        
        if (null == stu.getStudentIdCard())
        {
            throw new SecurityException("idcard is not exist. ");
        }
        
        idCardId = stu.getStudentIdCard().getId();
        
        // 解除学生和学生证关系
        stu.setStudentIdCard(null);
        stu.setLastModifyTime(System.currentTimeMillis());
        
        stuDao.updateStudent(stu);
        
        // 删除学生证
        idCardDao.delete(idCardId);
        
    }
    
    private StudentIdCard buildStuIdCard()
    {
        String id = UUID.randomUUID().toString();
        String num = System.currentTimeMillis() + "";
        
        StudentIdCard idcard = new StudentIdCard(id, num);
        
        return idcard;
    }
    
    private void addStuAndTeacher(List<String> teacherIds, String stuId, ITeacherDao teacherDao,
            IStudentAndTeacherDao stuAndteacherDao) throws SecurityException
    {
        if (null != teacherIds && !teacherIds.isEmpty())
        {
            String[] dbTeacherIds = teacherDao.findTeacherIdsByTeacherIds(teacherIds);
            
            if (teacherIds.size() != dbTeacherIds.length)
            {
                throw new SecurityException("teacherIds is error. ");
            }
            
            stuAndteacherDao.batchSaveStuAndTeacher(stuId, dbTeacherIds);
        }
    }
    
    public void delStu(String stuId)
    {
        Student dbStu = stuDao.findStudentById(stuId);
        
        if (null == dbStu)
        {
            throw new SecurityException("del stu error. stuId is not found. stuId:" + stuId);
        }
        
        // 处理老师的关系
        
        if (null != dbStu.getTeachers() && dbStu.getTeachers().size() > 0)
        {
            stuAndteacherDao.deleteByStudentId(dbStu.getId());
        }
        
        // 解除和学生证的关系
        // 解除与班级的关系
        boolean isUpdate = false;
        
        String idCardId = null;
        
        if (null != dbStu.getStudentIdCard())
        {
            idCardId = dbStu.getStudentIdCard().getId();
            dbStu.setStudentIdCard(null);
            isUpdate = true;
        }
        
        if (null != dbStu.getMyClass())
        {
            dbStu.setMyClass(null);
            isUpdate = true;
        }
        
        if (isUpdate)
        {
            stuDao.updateStudent(dbStu);
        }
        
        // 删除学生证
        if (null != idCardId)
        {
            idCardDao.delete(idCardId);
        }
        
        // 删除学生
        stuDao.delStudent(stuId);
    }
    
}
