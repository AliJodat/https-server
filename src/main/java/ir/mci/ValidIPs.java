package ir.mci;

import java.util.ArrayList;
import java.util.List;

public class ValidIPs {

    public static List<String> IP_LIST = new ArrayList<>();

    static {
        IP_LIST.add("127.0.0.1");
        IP_LIST.add("0:0:0:0:0:0:0:1");
    }

}
