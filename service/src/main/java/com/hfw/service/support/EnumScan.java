package com.hfw.service.support;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hfw.model.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class EnumScan implements ResourceLoaderAware {

    private Map<String,Class<?>> classMap = new HashMap<>();
    private Multimap<String,Class<?>> enumMap = HashMultimap.create();
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        try {
            MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            String scanPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX.concat(ClassUtils.convertClassNameToResourcePath("com.hfw.model.enums").concat("/**/*.class"));
            Resource[] resources = resolver.getResources(scanPath);
            for (Resource r : resources) {
                if(r.isReadable()){
                    MetadataReader metadataReader = metaReader.getMetadataReader(r);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    //不是接口或抽象类
                    if(classMetadata.isConcrete()){
                        Class<?> clazz = Class.forName(classMetadata.getClassName());
                        classMap.put(classMetadata.getClassName(), clazz);
                        enumMap.put(StrUtil.getFilenameExtension(classMetadata.getClassName()), clazz);
                    }
                }
            }
        }catch (Exception e){
            log.error("EnumScan error",e);
        }
    }

    public Class<?> getClass(String className){
        return classMap.get(className);
    }
    public Collection<Class<?>> getEnumClasses(String enumName){
        return enumMap.get(enumName);
    }

}
