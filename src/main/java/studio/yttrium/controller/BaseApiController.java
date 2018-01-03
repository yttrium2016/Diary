package studio.yttrium.controller;

import studio.yttrium.api.VerifierHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/2
 * Time: 20:27
 */
public class BaseApiController {

    @Resource
    private VerifierHelper verifierHelper;

    protected boolean verifier(HttpServletRequest request) {
        String requestDate = request.getHeader("requestDate");
        String userId = request.getHeader("userId");
        String uuid = request.getHeader("uuid");
        String verifier = request.getHeader("verifier");
        return verifierHelper.verifier(userId, uuid, requestDate, verifier);
    }

    protected boolean verifierLogin(HttpServletRequest request) {
        String requestDate = request.getHeader("requestDate");
        String uuid = request.getHeader("uuid");
        String verifier = request.getHeader("verifier");
        return verifierHelper.verifier(uuid, requestDate, verifier);
    }
}
