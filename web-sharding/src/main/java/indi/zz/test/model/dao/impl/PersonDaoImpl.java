package indi.zz.test.model.dao.impl;

import indi.zz.test.model.entity.Person;
import indi.zz.test.model.mapper.PersonMapper;
import indi.zz.test.model.dao.IPersonDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2022-09-23
 */
@Service
public class PersonDaoImpl extends ServiceImpl<PersonMapper, Person> implements IPersonDao {

}
