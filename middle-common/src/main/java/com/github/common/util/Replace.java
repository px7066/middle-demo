package catl.core.flow.condition;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>参数替换,参数以{xx}的形式</p>
 *
 * @author panxi
 * @version 1.0.0
 * @email panxi@hzwtech.com
 * @date 2022/7/15
 */
public class Replace {
    private String scriptTemplate;

    Pattern reg = Pattern.compile("\\{(.+?)\\}");

    public String getScriptTemplate() {
        return scriptTemplate;
    }

    public void setScriptTemplate(String scriptTemplate) {
        this.scriptTemplate = scriptTemplate;
    }

    public String getScript(Map<String, Object> map){
        Matcher matcher = reg.matcher(scriptTemplate);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()){
            String key = matcher.group(1);
            Object value = map.get(key);
            if(value == null){
                throw new IllegalArgumentException("条件判断参数：" + key + "未找到");
            }
            String replaceStr;
            if(value instanceof String){
                replaceStr = "'" + value + "'";
            }else{
                replaceStr = value.toString();
            }
            matcher.appendReplacement(sb, replaceStr);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void main(String[] args) {
        Condition condition = new Condition();
        condition.setScriptTemplate("{a} > 5");
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        System.out.println(condition.getScript(map));;
    }
}
