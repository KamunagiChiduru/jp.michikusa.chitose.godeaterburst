<#if pojo.needsToString()>
    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuffer buffer= new StringBuffer();

        buffer
            .append(getClass().getName())
            .append("@")
            .append(Integer.toHexString(hashCode()))
            .append(" [")
<#foreach property in pojo.getToStringPropertiesIterator()>
            .append("${property.getName()}='")
            .append(this.${property.getName()}())
            .append("' ")
</#foreach>
            ;
        buffer.append("]");
      
        return buffer.toString();
    }
</#if>
