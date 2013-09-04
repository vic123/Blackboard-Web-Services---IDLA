/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

import blackboard.persist.impl.mapping.FilteredDbObjectMap;
import blackboard.persist.impl.mapping.DbObjectMap;
import blackboard.persist.impl.mapping.DbMapping;
import blackboard.base.BbEnum;
import blackboard.platform.log.LogService;

import java.util.Calendar;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;

/**
 *
 * @author vic
 */


public class BbWsUtil {

    public static <T> T newClassInstanceWithThrow(Class<T> c) throws Exception {
            Object newobj = c.newInstance();
            return (T)newobj;  // unchecked cast

        
    }

    public static <T> T newClassInstance(Class<T> c)
//    throws InstantiationException,         IllegalAccessException,         java.lang.reflect.InvocationTargetException,         NoSuchMethodException
    {
        try {
            //http://forums.sun.com/thread.jspa?threadID=5338703:
            //1000000 regular instance creation: 				47 milliseconds.
            //1000000 constructor invocation using Class.newInstance(): 	318 milliseconds.
            //1000000 reflective constructor invocation without lookup:  	281 milliseconds.
            //1000000 reflective constructor invocation with lookup: 		5984 milliseconds.
            //Object newobj = c.getConstructor().newInstance();

            //http://stackoverflow.com/questions/195321/why-is-class-newinstance-evil :
            //In other words, it can defeat the checked exceptions system
            //Object newobj = c.newInstance();
            //return (T)newobj;  // unchecked cast
            return newClassInstanceWithThrow(c);
        //http://www.freshsources.com/Apr01.html :
        //Unchecked Exceptions: You are welcome to catch them (well, some of them), but the compiler won't make you do it.
        } catch (Throwable e) {
            BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.FATAL, e, "");
            return null;
        }
    }

    public static <T> T newClassInstance(Class<T> c, Object ... args)
//    throws InstantiationException,         IllegalAccessException,         java.lang.reflect.InvocationTargetException,         NoSuchMethodException
    {
        try {
            java.util.List<Class<?>> cargs_list = new java.util.ArrayList<Class<?>>();
            for (int i = 0; i < args.length; i++) cargs_list.add(args.getClass());
            //??Class<?>[] cargs = null;
            //??cargs = cargs_list.toArray(cargs);
            Class<?>[] cargs = cargs_list.toArray(new Class<?>[cargs_list.size()]);
            java.lang.reflect.Constructor<T> constr = c.getConstructor(cargs);
            Object newobj = constr.newInstance(args);
            return (T)newobj;  // unchecked cast
        } catch (Exception e) {
            BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.FATAL, e, "Error in BbWsUtil.newClassInstance()");
            return null;
        }
    }

    public static int nullSafeStringComparator(String txt, String otherTxt)
    {
        if ( txt == null )
            return otherTxt == null ? 0 : -1;
        if ( otherTxt == null )
              return 1;
        return txt.compareToIgnoreCase(otherTxt);
    }
    public static java.util.List convertLinkedHashMap2List(java.util.LinkedHashMap lhm) {
        return Arrays.asList(lhm.values().toArray());
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
        BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered BbWsUtil.constructExceptionMessage(Exception e) ");
        return constructExceptionMessage(e, "", 0);
    }

    public static FilteredDbObjectMap getFullFilteredMap(DbObjectMap dbObjectMap)
    {
        List<String> fields_list = new ArrayList();
        for (DbMapping db_map: dbObjectMap.getMappingList()) fields_list.add(db_map.getName());
        String fields[] = fields_list.toArray(new String[fields_list.size()]);
        FilteredDbObjectMap f_map = new FilteredDbObjectMap(dbObjectMap, fields);
        return f_map;
    }
    //public static <BbEnumClass extends BbEnum.class> int convertBbEnumFieldName2Int(String fieldName, BbEnumClass BbEnum.class enumClass) {
    //public static <BbEnumClass extends BbEnum> int convertBbEnumFieldName2Int(String fieldName, BbEnumClass enumClass) {
    public static int convertBbEnum2Int(BbEnum bbEnum) {
        int field_index = -1; 
        BbEnum bb_enums[] = BbEnum.getValues(bbEnum.getClass());
        for (int i = 0; i < bb_enums.length; i++) {
            if (bb_enums[i].compareTo(bbEnum) == 0) {
                field_index = i;
                break;
            }
        }
        /*
        if (field_index == -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bb_enums.length; i++) {
                sb.append(bb_enums[i].toFieldName() + ", ");
            }
            sb.delete(sb.length()-3, sb.length()-1);
            throw new BbWsException("Invalid field value. Possible values are: " + sb + ". Provided: " + fieldName);
        }*/
        return field_index;
    }

    public static <T extends BbEnum> List<String> getBbEnumFieldNames(Class<T> enumClass) throws IllegalAccessException {
    //public static List<String> getBbEnumFieldNames(Class<?> enumClass) throws IllegalAccessException {
        Field[] fields = enumClass.getFields();
        ArrayList<String> list = new ArrayList<String>(fields.length);
        for (Field f : fields) {
            if ((Modifier.isStatic(f.getModifiers())) && ((f.get(enumClass) instanceof BbEnum))) {
              String fname = f.getName();
              list.add(fname);
            }
        }
        return list;
    }
}
