package studio.yttrium.pojo;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/17
 * Time: 19:14
 */
public class WeatherInfo {

    private String data;
    private String fx;
    private String fl;
    private String hgih;
    private String low;
    private String type;

    public String getData() {
        return data;
    }

    public String getFl() {
        return fl;
    }

    public String getFx() {
        return fx;
    }

    public String getHgih() {
        return hgih;
    }

    public String getLow() {
        return low;
    }

    public String getType() {
        return type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public void setHgih(String hgih) {
        this.hgih = hgih;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setType(String type) {
        this.type = type;
    }
}
