package com.fastjavaframework.maker.support.util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 读取xml不验证dtd文件
 *
 * @author wangshuli
 */
public class DtdEntityResolver implements EntityResolver {
	@Override  
	 public InputSource resolveEntity(String publicId, String systemId)  
	   throws SAXException, IOException {  
	        return new InputSource(  
	             new ByteArrayInputStream(  
	                   "<?xml version='1.0' encoding='UTF-8'?>".getBytes()  
	    ));  
	 }  
}
