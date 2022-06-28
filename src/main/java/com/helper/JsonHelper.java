package com.helper;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import groovy.json.JsonSlurper;

public class JsonHelper
{
    public JsonHelper(){}
   
    public static List<?> lookUpValueFieldFromJson_fromList( List<Map<?,?>> jsonArray, String sField )
    {
        String sVal;
        List<?> sFilter = null;

        try {

            sFilter = jsonArray.stream().filter( map -> map.containsKey(sField) ).map( map -> map.get(sField) ).collect( Collectors.toList() );

        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage());
            e.printStackTrace();
        }
        if( sFilter != null && !sFilter.isEmpty() )
            return sFilter;
        else
            return null;
    }
    public static String lookUpValueFieldFromJson( String sJSON, String sField )
    {
        String sVal = null;
        String sResAux =  sJSON;

        JsonSlurper jsonParser = new JsonSlurper();

        try {
            Map jsonMap = ( Map ) jsonParser.parse( sResAux.getBytes("utf-8") );

            sVal = (String) jsonMap.get(sField);

        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage());
            e.printStackTrace();
        }
        if( sVal != null )
            return sVal;
        else
            return "";
    }
    public static Object[] lookUpValuesFieldFromJson( String sJSON, String sField )
    {
        String sVal;
        List<Object> sFilter = null;
        Object[] retval = {};
        String sResAux =  sJSON;

        JsonSlurper jsonParser = new JsonSlurper();

        try {
            List<Map> jsonMap = (List<Map>) jsonParser.parse( sResAux.getBytes("utf-8") );

            sFilter = jsonMap.stream().filter( map -> map.containsKey(sField)).map(map -> map.get(sField)).collect(Collectors.toList());

            retval = sFilter.toArray();

        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage());
            e.printStackTrace();
        }
        return retval;
    }
    public static List<?> lookUpValuesFieldFromGenericJson( String sJSON, String sField ) throws Exception
    {
        String sVal;
        ArrayList<?> sFilter = null;
        List<Map<?,?>> listAux = new ArrayList<>();
        String sResAux =  sJSON;
        JsonSlurper jsonParser = new JsonSlurper();

        if( jsonParser.parse( sResAux.getBytes("utf-8") ) instanceof ArrayList<?> )
        {
            listAux.addAll( parseJsonToArrayList( sJSON ) );
        }
        if( jsonParser.parse( sResAux.getBytes("utf-8") ) instanceof Map<?,?> )
        {
            listAux.add(parseJsonToMap( sJSON ));
        }
        sFilter = (ArrayList<?>) listAux.stream().filter(map -> map.containsKey(sField)).map(map -> map.get(sField)).collect(Collectors.toList());

        return sFilter;
    }
    public static Map<?, ?> parseJsonToMap( String sJSON ) throws Exception
    {
        String sVal;
        List<Object> sFilter = null;
        Map<String, ?> retval = new HashMap<>();
        String sResAux =  sJSON;
        JsonSlurper jsonParser = new JsonSlurper();
        retval = ( Map ) jsonParser.parse( sResAux.getBytes("utf-8") );
        return retval;
    }
    public static List<Map<?,?>> parseJsonToArrayList( String sJSON )
    {
        List<Map<?,?>> retval = new ArrayList<>();
        JsonSlurper jsonParser = new JsonSlurper();

        try {
            retval = (List<Map<?, ?>>) jsonParser.parse( sJSON.getBytes("utf-8") );
        } catch (UnsupportedEncodingException e) {
            System.out.print("UnsupportedEncodingException in lookUpValueField Method: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("Exception in lookUpValueFieldFromHttpResponse Method: " + e.getMessage());
            e.printStackTrace();
        }
        if( retval != null )
            return retval;
        else
            return null;
    }
    public static List<Map<?, ?>> convertJsonNodeToArray( Object sJsonObj ) throws Exception
    {
        String sVal;
        List<Map<?,?>> listAux = new ArrayList<Map<?,?>>();
        if( sJsonObj instanceof ArrayList<?> )
        {
            listAux.addAll( (Collection<? extends Map<?, ?>>) sJsonObj );
        }
        if( sJsonObj instanceof Map<?,?> )
        {
            listAux.add( (Map<?, ?>) sJsonObj );
        }
        return listAux;
    }
    public static List<String> removeAdjacentDuplicates( List<String> listIn)
    {
        List<String> acc = IntStream
                .range(0, listIn.size())
                .filter(i -> ((i < listIn.size() - 1 && !listIn.get(i).equals(listIn
                        .get(i + 1))) || i == listIn.size() - 1) )
                .mapToObj(i -> listIn.get(i))
                .collect(Collectors.toList());

        return acc;
    }
}
