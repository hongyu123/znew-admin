package com.hfw.model.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hfw.model.entity.GeneralException;
import com.hfw.model.jackson.ObjectMapperUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JsonMap extends HashMap<String, Object> {
    public JsonMap() {
        super();
    }
    public JsonMap(Map<String, Object> map) {
        super(map);
    }

    public static JsonMap create() {
        return new JsonMap();
    }

    public JsonMap putVal(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getString(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        }else if (value instanceof Date date) {
            return DateUtil.toString(date);
        }
        return value.toString();
    }

    public Double getDouble(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Double) {
            return (Double)value;
        } else if (value instanceof Number) {
            return ((Number)value).doubleValue();
        } else if (value instanceof String str) {
            return !str.isEmpty() && !"null".equalsIgnoreCase(str) ? Double.parseDouble(str) : null;
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to Double");
        }
    }

    public Long getLong(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Long) {
            return (Long)value;
        } else if (value instanceof Number) {
            return ((Number)value).longValue();
        } else if (value instanceof String str) {
            if (!str.isEmpty() && !"null".equalsIgnoreCase(str)) {
                return str.indexOf(46) != -1 ? (long)Double.parseDouble(str) : Long.parseLong(str);
            } else {
                return null;
            }
        } else if (value instanceof Boolean) {
            return (Boolean)value ? 1L : 0L;
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to Long");
        }
    }

    public Integer getInteger(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Integer) {
            return (Integer)value;
        } else if (value instanceof Number) {
            return ((Number)value).intValue();
        } else if (value instanceof String str) {
            if (!str.isEmpty() && !"null".equalsIgnoreCase(str)) {
                return str.indexOf(46) != -1 ? (int)Double.parseDouble(str) : Integer.parseInt(str);
            } else {
                return null;
            }
        } else if (value instanceof Boolean) {
            return (Boolean)value ? 1 : 0;
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to Integer");
        }
    }


    public Boolean getBoolean(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean)value;
        } else if (value instanceof Number) {
            return ((Number)value).intValue() == 1;
        } else if (value instanceof String str) {
            return !str.isEmpty() && !"null".equalsIgnoreCase(str) ? "true".equalsIgnoreCase(str) || "1".equals(str) : null;
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to boolean");
        }
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            if (value instanceof BigDecimal) {
                return (BigDecimal)value;
            } else if (value instanceof BigInteger) {
                return new BigDecimal((BigInteger)value);
            } else if (value instanceof Float f) {
                return new BigDecimal(Float.toString(f));
            } else if (value instanceof Double d) {
                return BigDecimal.valueOf(d);
            } else {
                long longValue = ((Number)value).longValue();
                return BigDecimal.valueOf(longValue);
            }
        } else if (value instanceof String str) {
            return new BigDecimal(str);
        } else if (value instanceof Boolean) {
            return (Boolean)value ? BigDecimal.ONE : BigDecimal.ZERO;
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to BigDecimal");
        }
    }

    public JsonMap getJsonMap(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof JsonMap map) {
            return map;
        } else if (value instanceof String str) {
            try {
                return ObjectMapperUtil.singleton().readValue(str, JsonMap.class);
            } catch (JsonProcessingException e) {
                throw new GeneralException(e);
            }
        } else if (value instanceof Map map) {
            return new JsonMap(map);
        } else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to JsonMap");
        }
    }

    public <T> List<T> getList(String key){
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof List) {
            return (List<T>)value;
        } else if (value instanceof Collection) {
            return new ArrayList<>((Collection<T>)value);
        } else if (value instanceof Object[]) {
            return new ArrayList<>((List<T>)Arrays.asList(value));
        } else if (value instanceof String str){
            try {
                return ObjectMapperUtil.singleton().readValue(str, ArrayList.class);
            } catch (JsonProcessingException e) {
                throw new GeneralException(e);
            }
        }
        else {
            throw new GeneralException("Can not cast '" + value.getClass() + "' to List");
        }
    }

}