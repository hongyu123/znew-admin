package com.hfw.basesystem.config;

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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 包扫描工具
 * @author farkle
 * @date 2023-02-24
 */
@Component
public class ScanSupport implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private ResourcePatternResolver resolver;
    private MetadataReaderFactory metaReader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metaReader = new CachingMetadataReaderFactory(resourceLoader);
    }

    /**
     * 扫描包下的类
     * @param pack
     * @return
     * @throws IOException
     */
    public Set<Class> scan(String pack) throws IOException {
        String scanPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX.concat(ClassUtils.convertClassNameToResourcePath(pack).concat("/**/*.class"));
        Resource[] resources = resolver.getResources(scanPath);
        Set<Class> classSet = new HashSet<>();
        for (Resource r : resources) {
            if(r.isReadable()){
                MetadataReader metadataReader = metaReader.getMetadataReader(r);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();
                //不是接口或抽象类
                if(classMetadata.isConcrete()){
                    classSet.add(classMetadata.getClass());
                }
            }
        }
        return classSet;
    }

}
