package cn.master.backend.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author create by 11's papa on 2022-11-23
 */
public class TestCaseConstants {
    public static final int MAX_NODE_DEPTH = 8;
    public static final double DEFAULT_POS = 65536;

    public enum Type {
        /**
         * 测试用例类型
         */
        Functional("functional"), Performance("performance"), Api("api");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static List<String> getValues() {
            List<Type> types = Arrays.asList(Type.values());
            return types.stream().map(Type::getValue).collect(Collectors.toList());
        }
    }

    public enum Method {
        /**
         * 测试用例执行方式
         */
        Manual("manual"), Auto("auto");

        private final String value;

        Method(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static List<String> getValues() {
            List<Method> types = Arrays.asList(Method.values());
            return types.stream().map(Method::getValue).collect(Collectors.toList());
        }
    }

    public enum StepModel {
        /**
         * 测试步骤
         */
        TEXT, STEP
    }
}
