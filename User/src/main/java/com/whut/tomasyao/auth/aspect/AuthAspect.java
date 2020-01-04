package com.whut.tomasyao.auth.aspect;

import edu.whut.pocket.auth.dao.IAdminRedisVoDao;
import edu.whut.pocket.auth.dao.IUserRedisVoDao;
import edu.whut.pocket.auth.model.UserType;
import edu.whut.pocket.auth.util.CookiesUtil;
import edu.whut.pocket.auth.vo.AdminRedisVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Component
@Aspect
public class AuthAspect {

    @Autowired
    private IUserRedisVoDao userRedisVoDao;
    @Autowired
    private IAdminRedisVoDao adminRedisVoDao;
    
    private static Logger logger = Logger.getLogger(AuthAspect.class.getName());

    @Around(value = "edu.whut.pocket.auth.aspect.AuthPointCut.authAccess()&&@annotation(authAnnotation)",
            argNames = "pjp,authAnnotation")
    public Object checkPermission(ProceedingJoinPoint pjp, AuthAnnotation authAnnotation) throws Throwable {
        UserType userType = authAnnotation.value();
        AuthCaseEnum cases = authAnnotation.cases();
        Object[] obj = pjp.getArgs();
        HttpServletRequest request = (HttpServletRequest) obj[0];
        logger.info("@checkPermission\turl\t" + request.getRequestURI());
        logger.info("@checkPermission\tuserType\t" + userType.name());
        logger.info("@checkPermission\tauthCases\t" + cases.name());
        String token = CookiesUtil.getToken(request);
        if (token == null) {
            logger.warning("@checkPermission\tproceed\tfalse!!!\tcause:token is null");
            return null;
        }
        /**如果某个方法上指定要root权限*/
        if(userType == UserType.root){
            AdminRedisVo adminRedisVo = adminRedisVoDao.get(token);
            //看redis里有没有记录
            if (adminRedisVo == null) {
                logger.warning("@checkPermission\tproceed\tfalse!!!\tcause:adminRedisVo is null");
                return null;
            }
            //看这个登录用户是否是root
            if(!userType.toString().equals(adminRedisVo.getType())){
                logger.warning("@checkPermission\tproceed\tfalse!!!\tcause:adminRedisVo not root");
                return null;
            }
            //看这个登录用户的id是否和redis里的一样
            if (cases == AuthCaseEnum.CASE_USER_ID) {
                int userId = (Integer) obj[1];//controller的第二个参数int userId
                if (userId != adminRedisVo.getId()) {
                    logger.warning("@checkPermission\tproceed\tfalse!!!\tcause:CASE_USER_ID");
                    return null;
                }
            }
            //看这个用户拥有的权限是否包含调用该方法所需要的权限
            int[] moduleIds = authAnnotation.moduleId();
            if (moduleIds.length != 0) {
                for (int moduleId : moduleIds) {
                    if (!adminRedisVo.getModuleSet().contains(moduleId)) {//只要有不包含的即鉴权失败
                        logger.warning("@checkPermission\tproceed\tfalse!!!\tcause:moduleIds");
                        return null;
                    }
                }
            }
        }

        return pjp.proceed();
    }


}
