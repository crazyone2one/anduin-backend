package cn.master.backend.service.impl;

import cn.master.backend.entity.TestCase;
import cn.master.backend.mapper.TestCaseMapper;
import cn.master.backend.service.TestCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseService {

}
