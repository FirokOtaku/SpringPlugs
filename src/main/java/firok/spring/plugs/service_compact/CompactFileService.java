package firok.spring.plugs.service_compact;

import firok.spring.plugs.bean.FileBean;
import firok.spring.plugs.mapper.FileMapper;
import firok.spring.plugs.mvci.GeneralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class CompactFileService
{
    @Autowired
    GeneralServiceImpl<FileMapper, FileBean> service;
    @Autowired
    DataSource ds;
}
