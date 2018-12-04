package com.github.vole.common.validate;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证转换工具类
 */
public class IdCardUtil {
   /** 
     * 根据15位的身份证号码获得18位身份证号码 
     * @param fifteenIDCard 15位的身份证号码 
     * @return 升级后的18位身份证号码 
     * @throws Exception 如果不是15位的身份证号码，则抛出异常 
     */  
    public static String getEighteenIDCard(String fifteenIDCard) throws Exception{  
       if(fifteenIDCard != null && fifteenIDCard.length() == 15){  
           StringBuilder sb = new StringBuilder();  
           sb.append(fifteenIDCard.substring(0, 6))  
             .append("19")  
             .append(fifteenIDCard.substring(6));  
           sb.append(getVerifyCode(sb.toString()));  
            return sb.toString();  
       } else {  
            throw new Exception("不是15位的身份证");  
       }  
    }
    
    
    /** 
      * 获取校验码 
      * @param idCardNumber 不带校验位的身份证号码（17位） 
      * @return 校验码 
      * @throws Exception 如果身份证没有加上19，则抛出异常 
      */  
       public static char getVerifyCode(String idCardNumber) throws Exception{
           if(idCardNumber == null || idCardNumber.length() < 17) {  
                throw new Exception("不合法的身份证号码");
            }  
            char[] Ai = idCardNumber.toCharArray();  
            int[] Wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};  
            char[] verifyCode = {'1','0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};  
            int S = 0;  
            int Y;  
            for(int i = 0; i < Wi.length; i++){  
                S += (Ai[i] - '0') * Wi[i];  
            }  
           Y = S % 11;  
            return verifyCode[Y];  
        }

   /**
    * 校验18位的身份证号码的校验位是否正确
    * @param idCardNumber 18位的身份证号码
    * @return
    * @throws Exception
    */
      public static boolean verify(String idCardNumber) throws Exception{
           if(idCardNumber == null || idCardNumber.length() != 18) {
              throw new Exception("不是18位的身份证号码");
           }
          return getVerifyCode(idCardNumber) == idCardNumber.charAt(idCardNumber.length() - 1);
     }

    /**
     * 校验18位的身份证号码的校验位是否正确
     * @param idCardNumber 18位的身份证号码
     * @return
     * @throws Exception
     */
    public static boolean verifyValid(String idCardNumber) {
        if(idCardNumber == null || idCardNumber.length() != 18) {
           return false;
        }
        boolean flag = false;
        try {
            flag = getVerifyCode(idCardNumber) == idCardNumber.charAt(idCardNumber.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

      /**
       * 根据18位身份证获取年龄和性别
       * @param idCard
       * @return
       * @throws Exception
       */
      public static Map<String, Object> getCarInfo(String idCard) throws Exception {
    	      if(idCard.length()==15){
    	    	  idCard = getEighteenIDCard(idCard);
    	      }
    	      Map<String, Object> map = new HashMap<String, Object>();  
    	      if(verify(idCard)){
                  String year = idCard.substring(6).substring(0, 4);// 得到年份  
                  String yue = idCard.substring(10).substring(0, 2);// 得到月份  
                  String sex;  
                  if (Integer.parseInt(idCard.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别  
                     sex = "f";  
                  } else {  
                     sex = "m";  
                }  
                  Date date = new Date();// 得到当前的系统时间  
                  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
                  String fyear = format.format(date).substring(0, 4);// 当前年份  
                  String fyue = format.format(date).substring(5, 7);// 月份  
                  int age = 0;  
                  if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生  
                      age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;  
                  } else {// 当前用户还没过生  
                      age = Integer.parseInt(fyear) - Integer.parseInt(year);  
                  }  
                  map.put("sex", sex);  
                  map.put("age", age);  
    	      }else {
    	    	   throw new Exception("身份证号码验证不正确");  
    	      }
    	      return map;   
          }  
      
      public static String idCardHide(String idCard){
    	  String idCardHide="";
    	  if(StringUtils.isNotBlank(idCard)){
    		  try{
    			  String str = idCard.substring(6, 14);
    			  idCardHide= idCard.replace(str, "******");
    		  }catch(Exception e){
    			  idCardHide = idCard;
    			 // System.out.println(e);
    		  }
    		  
    	  }
    	  return idCardHide;
      }
      
      
      
      
   public static void main(String[] args) {
	   String idCard = "421124198609086012";
	   String str =idCard.substring(6,14);
	   System.out.println(idCard.replace(str, "******")); 
	   
	   
//	  try {
//		 Map<String,Object> map = getCarInfo("421124198609086012");
//		System.out.println(map.get("sex").toString());
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
}  

}
