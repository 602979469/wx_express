package com.kaikeba.util;

import com.kaikeba.bean.*;
import com.kaikeba.service.ExpressService;

import java.util.List;

public class BootStrapUtil {

    public static BootStrapTableExpress castExpress(Express express) {
        String inTime = WebUtil.format(express.getInTime());
        String outTime = express.getOutTime() == null ? "未出库" : WebUtil.format(express.getOutTime());
        String status = express.getStatus() == 0 ? "待取件" : "已取件";
        String code = express.getCode() == null ? "已取件" : express.getCode();
        return new BootStrapTableExpress(express.getId(), express.getNumber(),
                express.getUsername(), express.getUserPhone(),
                express.getCompany(), code, inTime,
                outTime, status, express.getSysPhone());
    }

    public static BootStrapTableUser castUser(User user){
        List<Express> byUserPhone = ExpressService.findByUserPhone(user.getUserPhone());
        int expressNumber=byUserPhone==null?0:byUserPhone.size();
        return new BootStrapTableUser(user.getId(), user.getUsername(),
                user.getUserPhone(),user.getIdCardNumber(), user.getPassword(),expressNumber,
                WebUtil.format(user.getRegisterTime()), WebUtil.format(user.getLoginTime()));
    }

    public static BootStrapTableCourier castCourier(Courier courier){
        List<Express> bySysPhone = ExpressService.findBySysPhone(courier.getSysPhone());
        Integer sendNumber=bySysPhone==null?0:bySysPhone.size();
        return new BootStrapTableCourier(courier.getId(), courier.getName(),
                courier.getSysPhone(), courier.getIdCardNumber(), courier.getPassword(), sendNumber,
                WebUtil.format(courier.getRegisterTime()), WebUtil.format(courier.getLoginTime()));
    }
}
