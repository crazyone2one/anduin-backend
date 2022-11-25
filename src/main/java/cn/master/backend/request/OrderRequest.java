package cn.master.backend.request;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author create by 11's papa on 2022-11-24
 */
@Setter
public class OrderRequest {
    public String getName() {
        if (checkSqlInjection(name)) {
            return "1";
        }
        return name;
    }

    public String getType() {
        if (StringUtils.equalsIgnoreCase(type, "asc")) {
            return "ASC";
        } else {
            return "DESC";
        }
    }

    public String getPrefix() {
        if (checkSqlInjection(prefix)) {
            return "";
        }
        return prefix;
    }

    private String name;
    private String type;
    // 表前缀
    private String prefix;

    private static final Pattern PATTERN = Pattern.compile("^[\\w-]+$");
    public static boolean checkSqlInjection(String script) {
        if (StringUtils.isEmpty(script)) {
            return false;
        }
        return !PATTERN.matcher(script.toLowerCase()).find();
    }
}
