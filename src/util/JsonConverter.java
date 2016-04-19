package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.laozhang.struts2.base.model.Pagination;

public class JsonConverter {
	public static String convert2Json(Pagination pagination, String[] fieldName,boolean needSequence) {
		StringBuffer json = new StringBuffer();
		List list = pagination.getList();
		if (list != null && list.size() > 0) {
			json.append("{\r\n");
			json.append("\"page\":").append(pagination.getPageNumber()).append(",\r\n");
			json.append("\"total\":").append(pagination.getTotalCount()).append(",\r\n");
			json.append("\"rows\":[\r\n");
			for(int i=0;i<list.size();i++) {
				json.append("  {\"id\":\"");
	        	String[] s= fieldName[0].split(",");
	        	
			}
		}
		
		return json.toString();
	}
	
	private static String formatForZore(String format) {
		if(format.startsWith(".")){
			return "0"+format;
		}
		return format;
	}
	
	private static Object invokeMethod(Object owner, String fieldName, Object[] args) throws ClassNotFoundException, SecurityException, NoSuchFieldException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#,###,###.##");
		Class ownerClass = owner.getClass();
		Field field = ownerClass.getDeclaredField(fieldName);
		String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName);
		} catch (Exception e) {
			return "";
		}
		try {
			if ("java.util.Date".equals(field.getType().getName()) || "java.sql.Timestamp".equals(field.getType().getName())) {
				return method.invoke(owner) == null ? "" : format.format(method.invoke(owner));
			} else if ("java.math.BigDecimal".equals(field.getType().getName()) || "java.lang.Double".equals(field.getType().getName())) {
				return method.invoke(owner) == null ? 0.00 : formatForZore(df.format(method.invoke(owner)));
			} else {
				return method.invoke(owner) == null ? "" : method.invoke(owner);
			}
		} catch (Exception e) {
			return "";
		}
	}
	
	private static String filterSpecChar(String doStr) {
		doStr=doStr.replaceAll("\\\\", "\\\\\\\\");
		doStr=doStr.replaceAll("\r\n", " ");
		doStr=doStr.replaceAll("\t", " ");
		
		StringBuffer filtered = new StringBuffer(doStr.length());   
		char c;   
		for(int i=0;i<doStr.length();i++)   
        {   
			c=doStr.charAt(i);   
	        switch(c)   
	        {   
	        	case  '<' :filtered.append("&lt;");   break;   
	            case  '>' :filtered.append("&gt;");   break;   
	            //case  '\"':filtered.append("&quot;");   break;  
	            //case  '\'':filtered.append("&apos;");  break;
	            case  '&' :filtered.append("&amp;");   break;
	            case  '?' :filtered.append("&ques;");break;
	            case  '#' :filtered.append("&shap;");break;
	            //case  '_' :filtered.append("&ul;");break; 
	            case  '%' :filtered.append("&pc;");break;
	            default:   filtered.append(c);   
	        }   
               
        }  
		return filtered.toString();
	}
}
