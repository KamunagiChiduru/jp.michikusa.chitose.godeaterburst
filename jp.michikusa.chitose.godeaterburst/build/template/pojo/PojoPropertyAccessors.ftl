<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
  <#if pojo.hasFieldJavaDoc(property)>
    /**
    ${pojo.getFieldJavaDoc(property, 0)}を返す。
     */
  </#if>
    <#include "GetPropertyAnnotation.ftl"/>
    ${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${property.getName()}(){
        return this.${property.name};
    }

  <#if pojo.hasFieldJavaDoc(property)>
    /**
    ${pojo.getFieldJavaDoc(property, 0)}を設定する。
     */
  </#if>
    <#include "GetPropertyAnnotation.ftl"/>
    ${pojo.getPropertySetModifiers(property)} void ${property.getName()}(${pojo.getJavaTypeName(property, jdk5)} ${property.name}){
        this.${property.name}= ${property.name};
    }

    // this method is for hibernate
    @SuppressWarnings("unused")
    private ${pojo.getJavaTypeName(property, jdk5)} ${pojo.getGetterSignature(property)}(){
        return this.${property.getName()}();
    }

    // this method is for hibernate
    @SuppressWarnings("unused")
    private void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, jdk5)} ${property.getName()}){
        this.${property.getName()}(${property.getName()});
    }
    
</#if>
</#foreach>
