${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/>{

<#if !pojo.isInterface()>
<#include "PojoColumnNames.ftl"/>

<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   
<#include "PojoPropertyAccessors.ftl"/>

<#include "PojoToString.ftl"/>

<#include "PojoEqualsHashcode.ftl"/>

<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>

</#if>
<#include "PojoExtraClassCode.ftl"/>

<#-- <#include "PojoBuilder.ftl"/> -->

}
</#assign>

${pojo.generateImports()}
${classbody}

