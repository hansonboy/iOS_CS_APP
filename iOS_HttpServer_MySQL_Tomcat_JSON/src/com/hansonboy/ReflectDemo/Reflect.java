package com.hansonboy.ReflectDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reflect {
	public static void printClassName(Object object){
		System.out.println("class of"+object+"is"+object.getClass().getName());
		System.out.println(Integer.TYPE);
	}
	 public static Class getclass(String className){
	        Class c=null;
	        try {
	            c=Class.forName(className);
	        } catch (ClassNotFoundException ex) {
	            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return c;
	    }
	    
	    /**
	     * 
	     * @param name ��·��
	     * @param classParas Class����Ϣ�����б�
	     *  ����ǻ������������ǿ���ʹ����Tpye���ͣ������class�ֶ�����Ч��
	     *  ����Ƿ��������Ϳ���ʹ�õ�class�ֶ���������Class����Ϣ������Щ��Ҫ���ء�
	     * @param paras      ʵ�ʲ����б�����
	     * @return           ����Object���õĶ���ʵ��ʵ�ʴ��������Ķ������Ҫʹ�ÿ���ǿ��ת��Ϊ�Լ���Ҫ�Ķ���
	     * 
	     * �������ķ��䴴������
	     */
	    public static Object getInstance(String name,Class classParas[],Object paras[]){
	        Object o=null;
	        try {
	            Class c=getclass(name);
	            Constructor con=c.getConstructor(classParas);//��ȡʹ�õ�ǰ���췽�������������Constructor������������ȡ���캯����һЩ
	            try {
	                //��Ϣ
	                o=con.newInstance(paras);//���뵱ǰ���캯��Ҫ�Ĳ����б�
	            } catch (InstantiationException ex) {
	                Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IllegalAccessException ex) {
	                Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IllegalArgumentException ex) {
	                Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (InvocationTargetException ex) {
	                Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        } catch (NoSuchMethodException ex) {
	            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (SecurityException ex) {
	            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
	        return o;//���������Object���õĶ���
	    }
	    
	    /**
	     * 
	     * @param name ��·��
	     * @return ���������ķ��䴴������
	     */
	    public static Object getInstance(String name){
	        Class c=getclass(name);
	        Object o=null;
	        try {
	             o=c.newInstance();
	        } catch (InstantiationException ex) {
	            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return o;
	    }
	public static void exampleTest(){
        //�������������Ķ���
        //ReflectClass rc1=(ReflectClass) Reflect.getInstance("com.jijing.classDemo.ReflectClass");
        //System.out.println("ReflectClass111="+rc1);
		 System.out.println("******************************");
	        ReflectClass rc2=(ReflectClass)getInstance("com.hansonboy.ReflectDemo.ReflectClass",
	                                                              new Class[]{Integer.TYPE,String.class,MyClass.class},
	                                                              new Object[]{20,"����ReflectClass",new MyClass("����MyClass")});
	        System.out.println("ReflectClass222="+rc2);
	}
}
/**
 * 
 * @author Administrator
 *   �Զ���һ������
 */
class MyClass{
    private String name="";//������ʾ,�������������ɹ�
    MyClass(String name){
        this.name=name;
        show();//��ʾ
    }
    public void show(){
        System.out.println(name);
    }
    @Override
    public String toString(){
        return "MyClass="+name;
    }
}

/**
 * 
 * @author Administrator
 *  ͨ�����䴴������
 */
class ReflectClass{
    private String name="ReflectClass";
    public ReflectClass(int age,String name,MyClass my){
         this.name=name;
         show(age,name,my);
    }
    /**
     * 
     * @param age
     * @param name
     * @param my
     */
    public final void show(int age,String name,MyClass my){
        System.out.println("age="+age+" name="+name+" my="+my);
    }
    @Override
    public String toString(){
        return "ReflectClass="+name;
    }
}
