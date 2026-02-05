package com.hfw.model.mybatis.typehandler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.util.DateUtils;
import com.alibaba.fastjson2.util.TypeUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class DBMap extends HashMap<String, Object> {

    public DBMap putVal(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getString(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return (String)value;
        } else if (value instanceof Date) {
            long timeMillis = ((Date)value).getTime();
            return DateUtils.toString(timeMillis, false, DateUtils.DEFAULT_ZONE_ID);
        } else {
            return !(value instanceof Boolean) && !(value instanceof Character) && !(value instanceof Number) && !(value instanceof UUID) && !(value instanceof Enum) && !(value instanceof TemporalAccessor) ? JSON.toJSONString(value) : value.toString();
        }
    }

    public Double getDouble(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        } else if (value instanceof Double) {
            return (Double)value;
        } else if (value instanceof Number) {
            return ((Number)value).doubleValue();
        } else if (value instanceof String) {
            String str = (String)value;
            return !str.isEmpty() && !"null".equalsIgnoreCase(str) ? Double.parseDouble(str) : null;
        } else {
            throw new JSONException("Can not cast '" + value.getClass() + "' to Double");
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
        } else if (value instanceof String) {
            String str = (String)value;
            if (!str.isEmpty() && !"null".equalsIgnoreCase(str)) {
                return str.indexOf(46) != -1 ? (long)Double.parseDouble(str) : Long.parseLong(str);
            } else {
                return null;
            }
        } else if (value instanceof Boolean) {
            return (Boolean)value ? 1L : 0L;
        } else {
            throw new JSONException("Can not cast '" + value.getClass() + "' to Long");
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
        } else if (value instanceof String) {
            String str = (String)value;
            if (!str.isEmpty() && !"null".equalsIgnoreCase(str)) {
                return str.indexOf(46) != -1 ? (int)Double.parseDouble(str) : Integer.parseInt(str);
            } else {
                return null;
            }
        } else if (value instanceof Boolean) {
            return (Boolean)value ? 1 : 0;
        } else {
            throw new JSONException("Can not cast '" + value.getClass() + "' to Integer");
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
        } else if (!(value instanceof String)) {
            throw new JSONException("Can not cast '" + value.getClass() + "' to boolean");
        } else {
            String str = (String)value;
            return !str.isEmpty() && !"null".equalsIgnoreCase(str) ? "true".equalsIgnoreCase(str) || "1".equals(str) : null;
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
            } else if (value instanceof Float) {
                float floatValue = (Float)value;
                return TypeUtils.toBigDecimal(floatValue);
            } else if (value instanceof Double) {
                double doubleValue = (Double)value;
                return TypeUtils.toBigDecimal(doubleValue);
            } else {
                long longValue = ((Number)value).longValue();
                return BigDecimal.valueOf(longValue);
            }
        } else if (value instanceof String) {
            String str = (String)value;
            return TypeUtils.toBigDecimal(str);
        } else if (value instanceof Boolean) {
            return (Boolean)value ? BigDecimal.ONE : BigDecimal.ZERO;
        } else {
            throw new JSONException("Can not cast '" + value.getClass() + "' to BigDecimal");
        }
    }

}
