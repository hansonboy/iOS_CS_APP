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
	     * @param name 类路劲
	     * @param classParas Class类信息参数列表
	     *  如果是基本数据类型是可以使用其Tpye类型，如果用class字段是无效的
	     *  如果是非数据类型可以使用的class字段来创建其Class类信息对象，这些都要遵守。
	     * @param paras      实际参数列表数据
	     * @return           返回Object引用的对象，实际实际创建出来的对象，如果要使用可以强制转换为自己想要的对象
	     * 
	     * 带参数的反射创建对象
	     */
	    public static Object getInstance(String name,Class classParas[],Object paras[]){
	        Object o=null;
	        try {
	            Class c=getclass(name);
	            Constructor con=c.getConstructor(classParas);//获取使用当前构造方法来创建对象的Constructor对象，用它来获取构造函数的一些
	            try {
	                //信息
	                o=con.newInstance(paras);//传入当前构造函数要的参数列表
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
	        
	        return o;//返回这个用Object引用的对象
	    }
	    
	    /**
	     * 
	     * @param name 类路劲
	     * @return 不带参数的反射创建对象
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
        //创建不带参数的对象
        //ReflectClass rc1=(ReflectClass) Reflect.getInstance("com.jijing.classDemo.ReflectClass");
        //System.out.println("ReflectClass111="+rc1);
		 System.out.println("******************************");
	        ReflectClass rc2=(ReflectClass)getInstance("com.hansonboy.ReflectDemo.ReflectClass",
	                                                              new Class[]{Integer.TYPE,String.class,MyClass.class},
	                                                              new Object[]{20,"我是ReflectClass",new MyClass("我是MyClass")});
	        System.out.println("ReflectClass222="+rc2);
	}
}
/**
 * 
 * @author Administrator
 *   自定义一个类型
 */
class MyClass{
    private String name="";//名字显示,用来表明创建成功
    MyClass(String name){
        this.name=name;
        show();//显示
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
 *  通过反射创建对象
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
