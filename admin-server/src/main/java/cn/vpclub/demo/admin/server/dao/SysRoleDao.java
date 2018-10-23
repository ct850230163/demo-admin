package cn.vpclub.demo.admin.server.dao;


import cn.vpclub.demo.admin.feign.domain.entity.SysRoleEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * 系统角色 Mapper接口
 * @author yk
 * @since  2018-06-01
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createdBy);

}
