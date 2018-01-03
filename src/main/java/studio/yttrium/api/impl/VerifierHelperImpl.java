package studio.yttrium.api.impl;

import org.springframework.stereotype.Component;
import studio.yttrium.api.VerifierHelper;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/2
 * Time: 20:19
 */
@Component
public class VerifierHelperImpl implements VerifierHelper {
    @Override
    public boolean verifier(String uuid, String userId, String requestDate, String verifierStr) {
        return true;
    }

    @Override
    public boolean verifier(String uuid, String requestDate, String verifierStr) {
        return true;
    }
}
