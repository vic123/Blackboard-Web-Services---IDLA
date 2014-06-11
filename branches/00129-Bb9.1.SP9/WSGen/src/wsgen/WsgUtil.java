/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;

/**
 *
 * @author Administrator
 */
import bbwscommon.BbWsUtil;

import org.apache.commons.lang3.StringUtils;


//for sortLinkedHashMapByStringKey()
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Hashtable;
import java.util.Collections;
import java.util.Comparator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WsgUtil {
    //* static public String getMethodSignature(CtMethod method) throws Exception {
    static public String getMethodSignature(Method method) throws Exception {
        String m_signature = //method.getSignature(); - it is not "standard" readable
                method.getReturnType().getName() + " " 
                //+ method.getDeclaringClass().getName() + "." --this brings duplicate methods from super classes
                + method.getName() + "(";
        //* for (CtClass p_type: method.getParameterTypes()) {
        for (Class p_type: method.getParameterTypes()) {
            m_signature += p_type.getName() + ", ";
        }
        if (method.getParameterTypes().length > 0) {
            m_signature = m_signature.substring(0, m_signature.length()-2);
        }
        m_signature += ");";
        return m_signature;
    }
        public static String firstLetterUCase(String str) {
            String res = StringUtils.substring(str, 0, 1).toUpperCase()
                    + StringUtils.substring(str, 1, str.length());
                    //str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
            return res;
        }
        public static String firstLetterLCase(String str) {
            String res = StringUtils.substring(str, 0, 1).toLowerCase()
                    + StringUtils.substring(str, 1, str.length());
                    //str.substring(0, 1).toLowerCase()+ str.substring(1, str.length());
            return res;
        }


    public static <T extends String, O>
            void sortLinkedHashMapByStringKey1 (Map<String, O> lhm) {
        List<Map.Entry<String, O>> entry_list =
            new ArrayList<Map.Entry<String, O>>(lhm.entrySet());
            Collections.sort(entry_list, new Comparator<Map.Entry<String, O>>() {
                    public int compare(Map.Entry<String, O> a, Map.Entry<String, O> b){
                    String s = a.getKey();
                    String s1 = b.getKey();
                    int i = s.compareTo(s1);
                    return a.getKey().compareTo(b.getKey());
                }
            });
        lhm.clear();
        for (Map.Entry<String, O> entry : entry_list) {
            lhm.put(entry.getKey(), entry.getValue());
        }
    }

    public static <T extends Comparable<T>, O>
            void sortMapByComparableKey (Map<T, O> lhm) {
        List<Map.Entry<T, O>> entry_list
                =  new ArrayList<Map.Entry<T, O>>(lhm.entrySet());
            sortMapEntryListByKey(entry_list);
            /*Collections.sort(entry_list, new Comparator<Map.Entry<T, O>>() {
                    public int compare(Map.Entry<T, O> a, Map.Entry<T, O> b){
                    return a.getKey().compareTo(b.getKey());
                }
            });*/
        lhm.clear();
        for (Map.Entry<T, O> entry : entry_list) {
            lhm.put(entry.getKey(), entry.getValue());
        }
    }

    /*
    public static <T extends  String & Comparable<? extends T>, O> dfj() {

    }
    public static <String, O> d12fj() {

    }

    public static <T extends  String & Comparable<? extends T>, O>
            void sortLinkedHashMapByStringKey (LinkedHashMap<T, O> lhm) {
        List<Map.Entry<T, O>> entry_list =
            new ArrayList<Map.Entry<T, O>>(lhm.entrySet());
            Collections.sort(entry_list, new Comparator<Map.Entry<T, O>>() {
                    public int compare(Map.Entry<T, O> a, Map.Entry<T, O> b){
                    return a.getKey().compareTo(b.getKey());
                }
            });
        lhm.clear();
        for (Map.Entry<T, O> entry : entry_list) {
            lhm.put(entry.getKey(), entry.getValue());
        }
    }
*/
    //public static <T> T newClassInstanceWithThrow(Class<T> c) throws Exception {
    public static <T> T newClassInstanceWithThrow(Class<?> c, Class<? extends T> base_class) throws Exception {
        //http://www.angelikalanger.com/GenericsFAQ/FAQSections/TechnicalDetails.html#FAQ001
        //http://stackoverflow.com/questions/4388054/java-how-to-fix-the-unchecked-cast-warning
        T newobj = base_class.cast(c.newInstance());
        //T newobj = (T)c.newInstance();
        //T newobj = c.cast(c.newInstance());
        return newobj;  // unchecked cast
    }

    public static <T> T newClassInstanceWithThrow(Class<?> c,
            Class<? extends T> base_class,
            Object... arguments) throws Exception {
        Class arg_classes[] = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];
            Class cls = arg.getClass();
            arg_classes[i] = arg.getClass();
        }
        Constructor constructor = c.getConstructor(arg_classes);
        T newobj = base_class.cast(constructor.newInstance(arguments));
        //http://www.angelikalanger.com/GenericsFAQ/FAQSections/TechnicalDetails.html#FAQ001
        //http://stackoverflow.com/questions/4388054/java-how-to-fix-the-unchecked-cast-warning
        //T newobj = base_class.cast(c.newInstance());
        return newobj;  // unchecked cast
    }

/*
    void sortMapEnstryListByKey(List<Map.Entry<? extends Comparable, ?>> list) {
        Collections.sort(list, new Comparator<Map.Entry<? extends Comparable, ?>>() {
                public int compare(Map.Entry<? extends Comparable, ?> a,
                            Map.Entry<? extends Comparable, ?> b){
                    return a.getKey().compareTo(b.getKey());
            }
        });
    }
*/
    public static <T extends Comparable<T>, O> List<Map.Entry<T,O>>
            sortMapEntryListByKey(List<Map.Entry<T, O>> list) {
                Collections.sort(list, new Comparator<Map.Entry<T, O>>() {
                        public int compare(Map.Entry<T, O> a,
                                    Map.Entry<T, O> b){
                            return a.getKey().compareTo(b.getKey());
                    }
                });
                return list;
    }
    public static <T extends Comparable<T>> List<T>
            sortComparableList(List<T> list) {
                Collections.sort(list, new Comparator<T>() {
                        public int compare(T a, T b){
                            return a.compareTo(b);
                    }
                });
                return list;
    }

    private static String constructExceptionMessage(Throwable e, String res, int recDepth) {
        if (recDepth > 15) res = res + " \r\nMORE THAN 15 CAUSES - Possible dead loop in exceptions' \"cause chain\"";
        else {
            res = res + e.toString();
            if (e.getCause() != null) res = constructExceptionMessage(e.getCause(), res + " \r\nCAUSED BY: ", recDepth + 1);
        }
        return res;
    }

    public static String constructExceptionMessage(Exception e) {
        return constructExceptionMessage(e, "", 0);
    }

    private static Class primitiveTypeArr [] = {
            boolean.class,
            char.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            void.class
    };
    private static Hashtable<String, Class> primitiveTypeHT;
    static {
        primitiveTypeHT = new Hashtable<String, Class>(primitiveTypeArr.length + 1);
        for(int i = 0; i < primitiveTypeArr.length; i++) {
            primitiveTypeHT.put (primitiveTypeArr[i].getName(), primitiveTypeArr[i]);
        }
    }

    public static Class getClassForName(String cname) throws ClassNotFoundException {
        Class cl = primitiveTypeHT.get(cname);
        if (cl == null) cl = Class.forName(cname);
        return cl;
    }
    
    public static boolean isSubclassOf(Class subclass, Class superclass) {
        if (superclass == null)
            return false;

        String superName = superclass.getName();
        Class curr = subclass;
//        try {
            while (curr != null) {
                if (curr.getName().equals(superName))
                    return true;
                curr = curr.getSuperclass();
            }
//        } catch (Exception ignored) {}
        return false;
    }
/*not necessary - isSubclassOf should do a job
    public static boolean isSubtypeOf(Class st_clazz, Class clazz) throws NotFoundException {
        int i;
        String cname = clazz.getName();
        if (st_clazz == clazz || st_clazz.getName().equals(cname))
            return true;

        ClassFile file = getClassFile2();
        String supername = file.getSuperclass();
        if (supername != null && supername.equals(cname))
            return true;

        String[] ifs = file.getInterfaces();
        int num = ifs.length;
        for (i = 0; i < num; ++i)
            if (ifs[i].equals(cname))
                return true;

        if (supername != null && classPool.get(supername).subtypeOf(clazz))
            return true;

        for (i = 0; i < num; ++i)
            if (classPool.get(ifs[i]).subtypeOf(clazz))
                return true;

        return false;
    }
*/

/*
    public List<String> getBbEnumValues (String typeSignature) throws Exception {
        List<String> list = new ArrayList<String>();
            Class<blackboard.base.BbEnum> c = (Class<blackboard.base.BbEnum>)Class.forName(typeSignature);
            return BbWsUtil.getBbEnumFieldNames(c);
    }

    public static boolean isEnum(String typeSignature) throws Exception {
        CtClass cc = ClassPool.getDefault().get(typeSignature);
        return cc.getClass().isEnum();
    }
    public static boolean isBbEnum(String typeSignature) throws Exception {
        CtClass cc = ClassPool.getDefault().get(typeSignature);
        return cc.getClass().isEnum();
    }

    public static boolean isBbEnum(CtClass ctClass) throws Exception {
        if (ctClass == null) return false;
        if (ctClass.getName().equals("blackboard.base.BbEnum")) return true;
        else return isBbEnum(ctClass.getSuperclass());
    }
*/
}
