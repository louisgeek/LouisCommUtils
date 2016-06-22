/**
 * @version 1.0  2015年2月2日
 */
package com.louisgeek.louiscommutils.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * List根据参数排序      List去重复元素，保持顺序
 * @author louisgeek
 * 2015年2月2日下午5:03:11
 */
public class ListUitl<E> {   
	/**
	 * @param list  
	 * @param method  bean的getter方法   如"getOrderId"、 "getId"  ...
	 * @sort  "asc"、"ASC"或null正序   "desc"或"DESC"倒序 
	 */
    public void sort(List<E> list, final String method, final String sort){  
        Collections.sort(list, new Comparator() {             
            public int compare(Object a, Object b) {  
                int ret = 0;  
                try{  
                    Method m1 = ((E)a).getClass().getMethod(method, null);  
                    Method m2 = ((E)b).getClass().getMethod(method, null);  
                    if(sort != null &&"DESC".equals(sort)&& "desc".equals(sort)){
                    	//倒序  
                        ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a), null).toString());   
                    }
                    else{ 
                    	//正序
                        ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());  
                    }
                }catch(NoSuchMethodException ne){  
                    System.out.println(ne);  
                }catch(IllegalAccessException ie){  
                    System.out.println(ie);  
                }catch(InvocationTargetException it){  
                    System.out.println(it);  
                }  
                return ret;  
            }  
         });  
    } 
    /**
     * ArrayList去重复元素，保持顺序
     * @param list
     * @return
     */
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
         List newList = new ArrayList();
      for (Iterator iter = list.iterator(); iter.hasNext();) {
             Object element = iter.next();
             if (set.add(element))
                newList.add(element);
          } 
         list.clear();
         list.addAll(newList);
        System.out.println( "Louis_ListUtil_remove duplicate==" + list);
        return list;
   }
    /**
     * 循环删除重复元素
     * @param list
     * @return
     */
    public static List removeDuplicateFor(List list) {
    	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
    	     for ( int j = list.size() - 1 ; j > i; j -- ) {
    	       if (list.get(j).equals(list.get(i))) {
    	         list.remove(j);
    	       } 
    	      } 
    	    } 
    	    System.out.println("Louis_ListUtil_removeDuplicateFor=="+list);
    	    return list;
    	} 
    /**
     * 通过HashSet删除ArrayList中重复元素
     * @param list
     * @return
     */
    public static List removeDuplicateHashSet(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        System.out.println("Louis_ListUtil_removeDuplicateHashSet=="+list);
        return list;
  } 
}
