<#if pojo.needsEqualsHashCode() && !clazz.superclass?exists>
    @Override
    public boolean equals(Object other){
        if(this == other){ return true; }
        if(other == null){ return false; }
        if( !(other instanceof ${pojo.getDeclarationName()})){ return false; }
        ${pojo.getDeclarationName()} castOther= (${pojo.getDeclarationName()})other; 

        return
<#foreach field in pojo.getEqualsHashCodePropertiesIterator()>
            equals(this.${field.getName()}(), castOther.${field.getName()}()) && 
</#foreach>
            true // x && true == x
            ;
    }

    private static boolean equals(Object lhs, Object rhs){
        return (lhs == rhs) || (lhs != null && rhs != null && lhs.equals(rhs));
    }

    @Override
    public int hashCode(){
        int result= 17;

<#foreach field in pojo.getEqualsHashCodePropertiesIterator()>
        result= 37 * result + (this.${field.getName()}() != null ? this.${field.getName()}().hashCode() : 0);
</#foreach>

        return result;
   }   
</#if>
