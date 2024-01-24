package firok.spring.plugs.bean.service_compact;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import firok.spring.plugs.PlugsExceptions;
import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.mapper.UserMapper;
import firok.spring.plugs.mvci.GeneralServiceImpl;
import firok.topaz.general.CodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户数据相关
 * */
@Service
public class CompactUserService
{
    @Autowired
    GeneralServiceImpl<UserMapper, UserBean> service;

    /**
     * 根据 id 获取用户数据
     * */
    public UserBean getUserById(String id)
    {
        var qw = new QueryWrapper<UserBean>().lambda()
                .eq(UserBean::getId, id);
        return service.getOne(qw);
    }
    /**
     * 根据用户名获取用户数据
     * */
    public UserBean getUserByUsername(String username)
    {
        var qw = new QueryWrapper<UserBean>().lambda()
                .eq(UserBean::getUsername, username);
        return service.getOne(qw);
    }
    /**
     * 根据 id 列表获取用户数据
     * */
    public List<UserBean> getUserByIds(Collection<String> ids)
    {
        var qw = new QueryWrapper<UserBean>().lambda()
                .in(UserBean::getId, ids);
        return service.list(qw);
    }

    /**
     * 根据 id 删除用户数据
     * */
    public boolean deleteUserById(String id)
    {
        return service.removeById(id);
    }
    /**
     * 根据 id 批量删除用户数据
     * */
    public boolean deleteUserByIds(Collection<String> ids)
    {
        var qw = new QueryWrapper<UserBean>().lambda()
                .in(UserBean::getId, ids);
        return service.removeBatchByIds(ids, 50);
    }

    /**
     * 根据 id 更新用户数据
     * */
    public boolean updateUserById(UserBean user)
    {
        return service.generalUpdateById(user);
    }

    /**
     * 校验用户名密码是否可用
     * @return 查询到的用户数据
     * @throws CodeException 如果遇到问题则抛出
     * @apiNote 这个接口本身不会对获取到的用户数据做去敏处理, 调用者需要自行将密钥等字段清空
     * */
    public UserBean auth(String username, String password)
    {
        var user = getUserByUsername(username);
        PlugsExceptions.UserNotFound.maybe(user == null);
        assert user != null;
        PlugsExceptions.PasswordNotMatch.maybe(!Objects.equals(password, user.getPassword()));
        return user;
    }
}
