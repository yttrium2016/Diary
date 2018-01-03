package studio.yttrium.api;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/2
 * Time: 20:17
 */
public interface VerifierHelper {

    boolean verifier(String userId, String uuid, String requestDate, String verifierStr);

    boolean verifier(String uuid, String requestDate, String verifierStr);
}
