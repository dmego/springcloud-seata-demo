import cn.dmego.seata.common.util.IDUtils;

public class Test {
    public static void main(String[] args) {
        Long orderId = IDUtils.nextId();
        System.out.println(orderId);
        System.out.println(orderId.toString().length());
    }
}
